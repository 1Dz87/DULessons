package dao;

import entity.UserPhoto;

public interface PhotoDao {

    UserPhoto getUserPhoto(Long id);

    void saveUserPhoto(UserPhoto photo);
}
