package servlet;


import dao.UserDao;
import dao.impl.UserDaoFromDBImpl;
import entity.User;
import lib.Logging;
import lib.exception.BadCredentialsException;
import service.IUserService;
import service.impl.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

public class LoginServlet extends HttpServlet {

    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    private IUserService userService = new UserService();

    private Logging logging = Logging.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter("login");
        Optional<User> opt = userDao.findByLogin(login);
        try {
            User user = userService.login(req, resp, opt);
            resp.sendRedirect(req.getContextPath() + user.getRole().getRedirectUrl());
        } catch (BadCredentialsException e) {
            logging.getLogger().log(Level.SEVERE, e.getMessage());
            req.setAttribute("error", e.getMessage());
            RequestDispatcher dispatche = req.getRequestDispatcher("login.jsp");
            dispatche.forward(req, resp);
        }
    }
}
