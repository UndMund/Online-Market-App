package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.positionDto.PositionsEnumDto;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.exception.ServiceException;
import org.example.service.UserService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("positions", PositionsEnumDto.values());
        req.getRequestDispatcher(JspHelper.getPath("authorization/registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = UserDtoRegResponse.builder()
                .username(req.getParameter("username"))
                .position(req.getParameter("position"))
                .email(req.getParameter("email"))
                .phoneNumber(req.getParameter("phoneNumber"))
                .password(req.getParameter("password"))
                .build();

        try {
            var user = userService.create(userDto);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(UrlPath.MAIN);
        } catch (ServiceException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }

    }
}
