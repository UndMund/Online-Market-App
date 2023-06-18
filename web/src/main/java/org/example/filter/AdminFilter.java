package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.utils.UrlPath;

import java.io.IOException;

//@WebFilter(UrlPath.ADMIN_PROFILE + "*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isUserAdmin(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(UrlPath.MAIN);
        }
    }

    private boolean isUserAdmin(ServletRequest servletRequest) {
        var user = (UserDtoRequest)((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user.getPosition().equals("Admin");
    }

}
