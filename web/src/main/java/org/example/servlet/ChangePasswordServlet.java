package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.exception.ServiceException;
import org.example.service.UserService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.CHANGE_PASSWORD)
public class ChangePasswordServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("user/changePassword")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPassword = req.getParameter("password");
        HttpSession session = req.getSession();
        UserDtoRequest sessionUser = (UserDtoRequest) session.getAttribute("user");

        try {
            userService.updatePassword(UserDtoUpdatePasswordResponse.builder()
                    .id(sessionUser.getId())
                    .password(newPassword)
                    .build());
            req.setAttribute("message", "Password have already changed");
            doGet(req, resp);
        } catch (ServiceException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
