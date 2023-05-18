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
import java.util.List;

@WebServlet(UrlPath.MAIN)
public class MainServlet extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryDto> categories = categoryService.getCategories();
        req.getSession().setAttribute("categories", categories);
        req.getRequestDispatcher(JspHelper.getPath("mainMenu/main"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentCategory = req.getParameter("currentCategory");
        List<CategoryDto> categories = (List<CategoryDto>) req.getSession().getAttribute("categories");

        List<ProductDtoRequest> products = productService.getProductsByCategory(
                categories
                        .stream()
                        .filter(c -> c.getCategoryName().equals(currentCategory))
                        .findFirst().get()
        );

        req.getSession().setAttribute(
                "products",
                products
        );
        req.setAttribute("currentCategory", currentCategory);
        doGet(req, resp);
    }
}
