package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.exception.ValidationException;
import org.example.service.UserService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("authorization/login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDtoLoginResponse userDtoLoginResponse = UserDtoLoginResponse.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .build();
        try {
            UserDtoRequest user = userService.login(userDtoLoginResponse);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(UrlPath.MAIN);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
