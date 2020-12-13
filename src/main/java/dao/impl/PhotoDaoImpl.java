package dao.impl;

import dao.PhotoDao;
import entity.UserPhoto;
import lib.Logging;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class PhotoDaoImpl implements PhotoDao {

    private Logging logging = Logging.getInstance();

    private static PhotoDao instance;

    private static DataSource dataSource;

    private static Connection connection;

    private static Properties props = new Properties();

    public static PhotoDao getInstance() {
        if (instance == null) {
            InputStream in = null;
            try {
                instance = new PhotoDaoImpl();
                Context ctx = new InitialContext();
                ((PhotoDaoImpl)instance).dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb");
                connection = dataSource.getConnection();
                in = PhotoDaoImpl.class.getClassLoader().getResourceAsStream("user_photo.properties");
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

    private PhotoDaoImpl() {
    }

    @Override
    public UserPhoto getUserPhoto(Long id) {
        UserPhoto photo = null;

        try(PreparedStatement statement = connection.prepareStatement(props.getProperty("get"))) {
            statement.setLong(1, id);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                byte[] photoBytes = rs.getBytes("photo");
                photo = new UserPhoto(rs.getLong("id"), rs.getString("file_name"), photoBytes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }

    @Override
    public void saveUserPhoto(UserPhoto photo) {
        try(PreparedStatement statement = connection.prepareStatement(props.getProperty("save"), Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, photo.getFileName());
            statement.setLong(2, photo.getUser().getId());
            statement.setBytes(3, photo.getPhoto());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    photo.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Ошибка получения первичного ключа");
                }
            }
        } catch (Exception e) {
            logging.getLogger().warning(e.getMessage());
        }
    }
}
