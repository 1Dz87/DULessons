package servlet;

import dao.UserDao;
import dao.UserDaoFromDBImpl;
import entity.User;
import lib.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createuser")
public class CreateUserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = Boolean.parseBoolean(req.getParameter("isAdmin"));
        Role role = Role.getRoleByBooleanValue(isAdmin);
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                req.getParameter("first_name"), req.getParameter("last_name"), role);
        userDao.create(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/");
        requestDispatcher.forward(req, resp);

//        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}























