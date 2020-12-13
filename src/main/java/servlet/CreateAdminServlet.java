package servlet;

import dao.UserDao;
import dao.impl.UserDaoFromDBImpl;
import lib.exception.BadCredentialsException;
import service.IUserService;
import service.impl.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateAdminServlet extends HttpServlet {

    private final UserDao userDao = UserDaoFromDBImpl.getInstance();

    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.create(req, resp);
            resp.sendRedirect(req.getContextPath() + "/alluser");
        } catch (BadCredentialsException e) {
            req.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("createadmin.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
