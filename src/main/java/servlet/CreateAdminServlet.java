package servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;
import lib.Role;
import lib.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateAdminServlet extends HttpServlet {

    private final UserDao userDao = UserDaoFromDBImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String password = req.getParameter("password");
        final String login = req.getParameter("login");
        if (!Utils.stringIsEmpty(password) && !Utils.stringIsEmpty(login)) {
            final String hash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, password.toCharArray()));
            User user = new User(login, hash, req.getParameter("first_name"), req.getParameter("last_name"), Role.ADMIN);
            userDao.create(user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "Логин и пароль обязательны для заполнения");
            RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
