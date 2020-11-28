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

@WebServlet("/alluser")
public class FindAllUserServlet extends HttpServlet {
    private UserDao userDao = UserDaoFromDBImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final List<User> users = userDao.findAll();

        req.setAttribute("users", users);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
