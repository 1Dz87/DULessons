package dao;

import entity.User;
import entity.UserPhoto;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void create(User user);
    void delete(Long id);
    void update(User user);
    User findById(Long id);

    Optional<User> findByLogin(String login);

    List<User> findByLastName(String name);
    List<User> findAll();
}
