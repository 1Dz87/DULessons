package servlet;

import dao.UserDao;
import dao.impl.UserDaoFromDBImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/finduser")
public class FindUserServlet extends HttpServlet {

    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("finduser.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lastName = req.getParameter("lastName");
        final List<User> users = userDao.findByLastName(lastName);
        req.setAttribute("users", users);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("users.jsp");
        requestDispatcher.forward(req, resp);
    }
}












