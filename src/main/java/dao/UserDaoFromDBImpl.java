package dao;

import entity.User;
import entity.UserPhoto;
import lib.Logging;
import lib.Role;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class UserDaoFromDBImpl implements UserDao {

    private Logging logging = Logging.getInstance();

    private static UserDaoFromDBImpl instance;

    private static DataSource dataSource;

    private static Connection connection;

    public static synchronized UserDaoFromDBImpl getInstance() {
        if (instance == null) {
            try {
                instance = new UserDaoFromDBImpl();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb");
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private UserDaoFromDBImpl() {
    }

    @Override
    public void create(User user) {
        try(PreparedStatement statement = connection
                .prepareStatement("insert into user (login, password, first_name, last_name, role) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getRole().ordinal());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Ошибка получения первичного ключа");
                }
            }
        } catch (SQLException throwables) {
            String str = String.valueOf(Arrays.asList(throwables.getStackTrace()));
            logging.getLogger().log(Level.SEVERE, str);
        }
    }

    @Override
    public void delete(Long id) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("delete from user where id = " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try(Statement statement = connection.createStatement()) {
            Long imageId = user.getImage() == null ? null : user.getImage().getId();
            statement.execute("update user set first_name = '" + user.getFirstName() + "', " +
                    "last_name = '" + user.getLastName() + "', photo = " + imageId + " where id =" + user.getId() + ";");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        User user = null;

        try(Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where id = '" + id + "'");
            if (rs.next()){
                Role role = Role.values()[rs.getInt("role")];
                user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"), role);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByLogin(String login) {
        User user = null;

        try(Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where login = '" + login + "'");
            if (rs.next()){
                Role role = Role.values()[rs.getInt("role")];
                user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"), role);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        ArrayList<User> list = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where last_name = '" + lastName + "'");

            while (rs.next()){
                Role role = Role.values()[rs.getInt("role")];
                User user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"), role);
                list.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> findAll() {
        ArrayList<User> list = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user");

            while (rs.next()){
                Role role = Role.values()[rs.getInt("role")];
                User user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"), role);
                Long photoId = rs.getLong("photo");
                UserPhoto userPhoto = getUserPhoto(photoId);
                user.setImage(userPhoto);
                list.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public UserPhoto getUserPhoto(Long id) {
        UserPhoto photo = null;

        try(Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from photo where id = '" + id + "'");
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
        try(PreparedStatement statement = connection.prepareStatement("insert into photo (file_name, user, photo) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

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
