package lib;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (uri.endsWith("/") || uri.endsWith("login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            if (((new Date().getTime() - session.getLastAccessedTime()) / 1000) <= 30 * 60
                    && session.getAttribute("UID") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("login.jsp");
                dispatcher.forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
