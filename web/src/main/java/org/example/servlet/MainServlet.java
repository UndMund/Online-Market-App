package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.categoryDto.CategoryDtoRequest;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;
import java.util.List;

@WebServlet(UrlPath.MAIN)
public class MainServlet extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> categories = categoryService.getCategories()
                .stream()
                .map(CategoryDtoRequest::getCategoryName)
                        .toList();
        req.getSession().setAttribute("categories", categories);
        req.getRequestDispatcher(JspHelper.getPath("mainMenu/main"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = (String) req.getParameter("currentCategory");
        List<ProductDtoRequest> products = null; // = productService.getProductsByCategory(CategoryDtoResponse.find(category).get());
        req.getSession().setAttribute(
                "products",
                products
        );
        req.setAttribute("currentCategory", category);
        doGet(req, resp);
    }
}
