package service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDao;
import dao.impl.UserDaoFromDBImpl;
import entity.User;
import lib.Logging;
import lib.Role;
import lib.Utils;
import lib.exception.BadCredentialsException;
import service.IUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

public class UserService implements IUserService {

    private final UserDao userDao = UserDaoFromDBImpl.getInstance();

    private Logging logging = Logging.getInstance();

    @Override
    public void create(HttpServletRequest req) throws BadCredentialsException {
        final String password = req.getParameter("password");
        final String login = req.getParameter("login");
        if (!Utils.stringIsEmpty(password) && !Utils.stringIsEmpty(login)) {
            final String hash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, password.toCharArray()));
            User user = new User(login, hash, req.getParameter("first_name"), req.getParameter("last_name"), Role.ADMIN);
            userDao.create(user);
        } else throw new BadCredentialsException("Логин и пароль обязательны для заполнения");
    }

    @Override
    public User login(HttpServletRequest req, HttpServletResponse resp, Optional<User> opt) throws BadCredentialsException, IOException {
        final String pass = req.getParameter("password");
        if (Utils.stringIsEmpty(pass)) {
            throw new BadCredentialsException("Логин и пароль обязательны для заполнения");
        }
        if (opt.isPresent()) {
            User user = opt.get();
            BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), user.getPassword());
            String loginHash = new String(BCrypt.with(BCrypt.Version.VERSION_2B).hash(13, user.getLogin().toCharArray()));
            if (result.verified) {
                String hash = loginHash + new Date().getTime();
                HttpSession session = req.getSession();
                session.setAttribute("UID", hash);
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute("user", user);

                Cookie cookie = new Cookie("UID", hash);
                cookie.setMaxAge(30 * 60);
                resp.addCookie(cookie);
                logging.getLogger().log(Level.INFO, "Вход в приложение пользователем: " + user.getLogin());
                return user;
            } else {
                throw new BadCredentialsException("Не верный логин или пароль");
            }
        } else throw new BadCredentialsException("Не верный логин или пароль");
    }
}
