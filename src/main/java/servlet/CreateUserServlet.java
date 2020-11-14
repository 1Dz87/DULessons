package servlet;

import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;
import entity.UserPhoto;
import lib.Role;
import lib.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;

@WebServlet("/createuser")
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 11000000)
public class CreateUserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("createuser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = Utils.checkboxToBoolean(req.getParameter("isAdmin"));
        Role role = Role.getRoleByBooleanValue(isAdmin);
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                req.getParameter("first_name"), req.getParameter("last_name"), role);
        userDao.create(user);
        Part photoPart = req.getPart("photo");
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
        userDao.saveUserPhoto(userPhoto);
        user.setImage(userPhoto);
        userDao.update(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/");
        requestDispatcher.forward(req, resp);
    }
}























