package servlet;

import dao.UserDao;
import dao.UserDaoFromDBImpl;
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

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String lastName = req.getParameter("lastName");

        final List<User> users = userDao.findByLastName(lastName);

        req.setAttribute("users", users);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("users.jsp");
        requestDispatcher.forward(req, resp);
    }
}












