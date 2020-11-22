package lib;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        final String uri = ((HttpServletRequest) req).getRequestURI();
        if (uri.endsWith("/") || uri.endsWith("login") || uri.endsWith("createAdmin")) {
            filterChain.doFilter(req, resp);
        } else {
            HttpSession session = ((HttpServletRequest) req).getSession();
            if (session != null && ((new Date().getTime() - session.getLastAccessedTime()) / 1000) <= session.getMaxInactiveInterval()) {
                final String uid = (String) session.getAttribute("UID");
                Cookie[] cookies = ((HttpServletRequest) req).getCookies();
                Optional<Cookie> opt = Arrays.stream(cookies).filter(cookie -> uid.equals(cookie.getValue())).findAny();
                if (opt.isPresent()) {
                    filterChain.doFilter(req, resp);
                } else {
                    sendError(req, resp);
                }
            } else {
                sendError(req, resp);
            }
        }
    }

    private void sendError(ServletRequest req, ServletResponse resp) throws IOException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        session.setAttribute("error", "Выполните вход");
        ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
