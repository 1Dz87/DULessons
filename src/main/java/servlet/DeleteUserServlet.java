package servlet;

import dao.UserDao;
import dao.UserDaoFromDBImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoFromDBImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDao.delete(Long.valueOf(req.getParameter("id")));

        resp.sendRedirect("/alluser");
    }
}
