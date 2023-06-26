package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import static org.example.utils.UrlPath.MAIN;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"categories"})
public class MainPageController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping(MAIN)
    public String mainPage(Model model) {
        List<String> categories = categoryService.getCategories();
        List<ProductDtoRequest> products = productService.getProductsByStatus(StatusDto.ON_SALE);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "main/main";
    }

    //ProductsCategory controller maybe и в нем обработка категорий и передача уже сюда в get прием продуктов из модели

    /*@Override
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
    }*/
}
