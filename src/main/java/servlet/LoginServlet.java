package servlet;


import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;
import lib.Logging;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

public class LoginServlet extends HttpServlet {

    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    private Logging logging = Logging.getInstance();

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
                String hash = loginHash + new Date().getTime();
                HttpSession session = req.getSession();
                session.setAttribute("UID", hash);
                session.setMaxInactiveInterval(30 * 60);

                Cookie cookie = new Cookie("UID", hash);
                cookie.setMaxAge(30 * 60);
                resp.addCookie(cookie);
                logging.getLogger().log(Level.INFO, "Вход в приложение пользователем: " + user.getLogin());
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
