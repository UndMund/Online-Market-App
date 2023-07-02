package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.filter.PriceFilterEnum;
import org.example.dto.filter.ProductFilter;
import org.example.dto.productDto.PageResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

import static org.example.utils.UrlPath.MAIN;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"categories", "sessionProductFilter", "sessionPage"})
public class MainPageController {
    private static final int DEFAULT_PAGE_SIZE = 4;
    private static final int DEFAULT_START_PAGE = 0;
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping(MAIN)
    public String defaultMainPage(Model model,
                                  @SessionAttribute(name = "sessionPage", required = false) Integer page) {
        return "redirect:" + MAIN + Objects.requireNonNullElse(page, DEFAULT_START_PAGE);
    }

    @GetMapping(MAIN + "{page}")
    public String mainPage(@PathVariable("page") Integer page,
                           @ModelAttribute("productFilter") ProductFilter productFilter,
                           @SessionAttribute(name = "sessionProductFilter", required = false) ProductFilter sessionProductFilter,
                           Model model) {

        Slice<ProductDtoRequest> slice;
        ProductFilter actualProductFilter;

        if (!productFilter.isEmpty()) {
            actualProductFilter = productFilter;
            slice = productService.getProductSliceByFilter(page, DEFAULT_PAGE_SIZE, actualProductFilter);

        } else if (sessionProductFilter != null) {
            actualProductFilter = sessionProductFilter;
            slice = productService.getProductSliceByFilter(page, DEFAULT_PAGE_SIZE, actualProductFilter);

        } else {
            actualProductFilter = new ProductFilter(null, null);
            slice = productService.getProductSlice(PageRequest.of(page, DEFAULT_PAGE_SIZE));
        }

        if (!model.containsAttribute("categories")) {
            List<String> categories = categoryService.getCategories();
            model.addAttribute("categories", categories);
        }

        model.addAttribute("productFilter", actualProductFilter);
        model.addAttribute("products", PageResponse.of(slice));
        model.addAttribute("sessionPage", page);
        model.addAttribute("priceFilterValues", PriceFilterEnum.values());
        return "main/main";
    }

    @PostMapping(MAIN + "{page}")
    public String mainPageSelectPage(RedirectAttributes redirectAttributes,
                                     @ModelAttribute("productFilter") ProductFilter productFilter,
                                     @PathVariable("page") Integer page,
                                     Model model) {
        redirectAttributes.addFlashAttribute("productFilter", productFilter);
        model.addAttribute("sessionProductFilter", productFilter);
        return "redirect:" + MAIN + page;
    }
}
