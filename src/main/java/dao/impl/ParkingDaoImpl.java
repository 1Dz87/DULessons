package dao.impl;

import dao.ParkingDao;
import entity.Parking;
import entity.ParkingArea;
import entity.ParkingPlace;
import lib.Utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ParkingDaoImpl implements ParkingDao {

    private static ParkingDaoImpl instance;

    private static DataSource dataSource;

    private static Connection connection;

    private static Properties props = new Properties();

    public static ParkingDaoImpl getInstance() {
        if (instance == null) {
            InputStream in = null;
            try {
                instance = new ParkingDaoImpl();
                Context ctx = new InitialContext();
                ((ParkingDaoImpl)instance).dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb");
                connection = dataSource.getConnection();
                in = ParkingDaoImpl.class.getClassLoader().getResourceAsStream("parking_dao.properties");
                props.load(in);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    private ParkingDaoImpl() {

    }

    @Override
    public Parking saveParking(Parking parking) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement parkingStatement = connection.prepareStatement(props.getProperty("create_new_parking"), Statement.RETURN_GENERATED_KEYS);
            parkingStatement.setString(1, parking.getName());
            parkingStatement.executeUpdate();

            try (ResultSet parkingGenKeys = parkingStatement.getGeneratedKeys()) {
                if (parkingGenKeys.next()) {
                    parking.setId(parkingGenKeys.getLong(1));
                    for (ParkingArea area : parking.getParkingAreas()) {
                        PreparedStatement areaStatement = connection.prepareStatement(props.getProperty("create_new_area"), Statement.RETURN_GENERATED_KEYS);
                        areaStatement.setString(1, area.getSide().name());
                        areaStatement.setLong(2, parking.getId());
                        areaStatement.executeUpdate();
                        ResultSet areaGenKeys = areaStatement.getGeneratedKeys();
                        if (areaGenKeys.next()) {
                            area.setId(areaGenKeys.getLong(1));
                        } else {
                            throw new SQLException("Ошибка получения первичного ключа");
                        }
                        for (ParkingPlace place : area.getPlaces()) {
                            PreparedStatement placeStmt = connection.prepareStatement(props.getProperty("create_parking_place"), Statement.RETURN_GENERATED_KEYS);
                            placeStmt.setInt(1, place.getNumber());
                            placeStmt.setLong(2, area.getId());
                            placeStmt.setBoolean(3, place.isOccupied());
                            placeStmt.executeUpdate();
                            ResultSet placeGenKeys = placeStmt.getGeneratedKeys();
                            if (placeGenKeys.next()) {
                                place.setId(placeGenKeys.getLong(1));
                            } else {
                                throw new SQLException("Ошибка получения первичного ключа");
                            }
                        }
                    }
                } else {
                    throw new SQLException("Ошибка получения первичного ключа");
                }
            } catch (Exception e) {
                connection.rollback();
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return parking;
    }

    @Override
    public Parking getParking(Long id) {
        return null;
    }

    @Override
    public Parking getParking(String name) {
        return null;
    }
}
