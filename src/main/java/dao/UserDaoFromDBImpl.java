package dao;

import entity.User;
import entity.UserPhoto;
import lib.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoFromDBImpl implements UserDao {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/webdb?serverTimezone=UTC";

    static final String USER = "root";
    static final String PASSWORD = "root";

    @Override
    public void create(User user) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into user (login, password, first_name, last_name, role) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

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
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            statement.execute("delete from user where id = " + id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {
            Long imageId = user.getImage() == null ? null : user.getImage().getId();
            statement.execute("update user set first_name = '" + user.getFirstName() + "', " +
                    "last_name = '" + user.getLastName() + "', photo = " + imageId + " where id =" + user.getId() + ";");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User user = null;

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where id = '" + id + "'");
            if (rs.next()){
                user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByLogin(String login) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User user = null;

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where login = '" + login + "'");
            if (rs.next()){
                user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<User> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user where last_name = '" + lastName + "'");

            while (rs.next()){
                User user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"));
                list.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> findAll() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<User> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            final ResultSet rs = statement.executeQuery("select * from user");

            while (rs.next()){
                User user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"));
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
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        UserPhoto photo = null;

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

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
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into photo (file_name, user, photo) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
