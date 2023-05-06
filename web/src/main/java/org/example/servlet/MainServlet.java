package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(UrlPath.MAIN)
public class MainServlet extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> categories = new ArrayList<>(categoryService.getCategories()
                .stream()
                .map(CategoryDto::getName)
                .toList());
        req.getSession().setAttribute("categories", categories);
        req.getRequestDispatcher(JspHelper.getPath("mainMenu/main"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = (String) req.getParameter("currentCategory");
        List<ProductDtoRequest> products = productService.getProductsByCategory(CategoryDto.find(category).get());
        req.getSession().setAttribute(
                "products",
                products
        );
        req.setAttribute("currentCategory", category);
        doGet(req, resp);
    }
}
