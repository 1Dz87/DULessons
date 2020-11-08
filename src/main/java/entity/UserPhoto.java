package entity;

import java.util.Arrays;

public class UserPhoto {

    private Long id;

    private String fileName;

    private byte[] photo;

    private User user;

    public UserPhoto() {
    }

    public UserPhoto(Long id, String fileName, byte[] photo) {
        this.id = id;
        this.fileName = fileName;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserPhoto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
