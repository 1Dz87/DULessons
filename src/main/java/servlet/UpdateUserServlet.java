package servlet;

import dao.PhotoDao;
import dao.UserDao;
import dao.impl.PhotoDaoImpl;
import dao.impl.UserDaoFromDBImpl;
import entity.User;
import entity.UserPhoto;
import service.IUploadImageService;
import service.impl.UploadImageInterface;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet("/updateuser")
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 11000000)
public class UpdateUserServlet extends HttpServlet {

    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    private IUploadImageService uploadImageService = new UploadImageInterface();

    private static PhotoDao photoDao = PhotoDaoImpl.getInstance();

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

        UserPhoto photo = uploadImageService.uploadImageAndCreateObj(photoPart, user);
        photoDao.saveUserPhoto(photo);
        user.setImage(photo);
        userDao.update(user);

        resp.sendRedirect(req.getContextPath() + "/alluser");
    }
}
