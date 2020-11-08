package servlet;

import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;
import entity.UserPhoto;

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
import java.util.List;

@WebServlet("/updateuser")
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 11000000)
public class UpdateUserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = userDao.findById(Long.valueOf(req.getParameter("id")));

        req.setAttribute("user", user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/updateuser.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDao.findById(Long.valueOf(req.getParameter("id")));

        user.setFirstName(req.getParameter("first_name"));
        user.setLastName(req.getParameter("last_name"));

        Part photoPart = req.getPart("photo");
        String fileName = photoPart.getSubmittedFileName();

        UserPhoto photo = new UserPhoto();
        photo.setFileName(fileName);
        photo.setUser(user);

        OutputStream out = null;
        InputStream in = null;
        File tempFile = null;
        try {
            tempFile = Files.createTempFile("temp_file", null).toFile();
            out = new FileOutputStream(tempFile);
            in = photoPart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            byte[] photoBytes = Files.readAllBytes(tempFile.toPath());
            photo.setPhoto(photoBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (tempFile != null) {
                tempFile.delete();
            }
        }
        userDao.saveUserPhoto(photo);
        user.setImage(photo);
        userDao.update(user);

        resp.sendRedirect(req.getContextPath() + "/alluser");
    }
}
