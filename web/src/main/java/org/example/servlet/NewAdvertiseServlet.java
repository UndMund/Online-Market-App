package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dto.productDto.ProductDtoResponse;
import org.example.dto.userDto.UserDtoCreateProductResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.exception.ValidationException;
import org.example.service.ProductService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.NEW_AD)
public class NewAdvertiseServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("user/newAdvertisement")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDtoRequest sessionUser = (UserDtoRequest) session.getAttribute("user");
        ProductDtoResponse newProduct = ProductDtoResponse.builder()
                .name(req.getParameter("name"))
                .price(req.getParameter("price"))
                .description(req.getParameter("description"))
                .category(req.getParameter("category"))
                .user(new UserDtoCreateProductResponse(
                        sessionUser.getId()
                ))
                .build();

        try {
            productService.createProduct(newProduct);
            req.setAttribute("message", "Ad has already created. Wait for verification, please");
            doGet(req, resp);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
