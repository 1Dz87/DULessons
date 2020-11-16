package servlet;


import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class LoginServlet extends HttpServlet {

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter("login");
        final String pass = req.getParameter("password");

        User user = userDao.findByLogin(login);
        if (user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), user.getPassword());
            String loginHash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, user.getLogin().toCharArray()));
            if (result.verified) {
                HttpSession session = req.getSession();
                session.setAttribute("UID", loginHash + new Date().getTime());
                session.setMaxInactiveInterval(30 * 60);
                req.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + user.getRole().getRedirectUrl());
            } else {
                req.setAttribute("error", "Не верный логин или пароль");
                RequestDispatcher dispatche = req.getRequestDispatcher("login.jsp");
                dispatche.forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Не верный логин или пароль");
            RequestDispatcher dispatche = req.getRequestDispatcher("login.jsp");
            dispatche.forward(req, resp);
        }
    }
}
