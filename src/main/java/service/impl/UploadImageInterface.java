package service.impl;

import entity.User;
import entity.UserPhoto;
import service.IUploadImageService;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;

public class UploadImageInterface implements IUploadImageService {

    @Override
    public UserPhoto uploadImageAndCreateObj(Part photoPart, User user) throws IOException {
        String fileName = photoPart.getSubmittedFileName();

        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setFileName(fileName);
        userPhoto.setUser(user);

        OutputStream out = null;
        InputStream in = null;
        File temp = null;
        try {
            temp = Files.createTempFile("temp_file", null).toFile();
            out = new FileOutputStream(temp);
            in = photoPart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = in.read(bytes))!= -1) {
                out.write(bytes, 0, read);
            }
            byte[] photoBytes = Files.readAllBytes(temp.toPath());
            userPhoto.setPhoto(photoBytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (temp != null) {
                temp.delete();
            }
        }
        return userPhoto;
    }
}
