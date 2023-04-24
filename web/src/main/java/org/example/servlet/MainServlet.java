package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.CategoryService;
import org.example.utils.JspHelper;
import org.example.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.MAIN)
public class MainServlet extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("categories", categoryService.getCategories());
        req.getRequestDispatcher(JspHelper.getPath("mainMenu/main"))
                .forward(req, resp);
    }
}
