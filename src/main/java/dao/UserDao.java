package dao;

import entity.User;
import entity.UserPhoto;

import java.util.List;

public interface UserDao {
    void create(User user);
    void delete(Long id);
    void update(User user);
    User findById(Long id);

    User findByLogin(String login);

    List<User> findByLastName(String name);
    List<User> findAll();
    void saveUserPhoto(UserPhoto photo);
    UserPhoto getUserPhoto(Long id);
}
