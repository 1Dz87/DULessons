package service;

import entity.User;
import lib.exception.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface IUserService {

    void create(HttpServletRequest req, HttpServletResponse resp) throws BadCredentialsException;

    User login(HttpServletRequest req, HttpServletResponse resp, Optional<User> opt) throws BadCredentialsException, IOException;
}
