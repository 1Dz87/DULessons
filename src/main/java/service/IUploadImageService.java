package service;

import entity.User;
import entity.UserPhoto;

import javax.servlet.http.Part;
import java.io.IOException;

public interface IUploadImageService {

    UserPhoto uploadImageAndCreateObj(Part photoPart, User user) throws IOException;
}
