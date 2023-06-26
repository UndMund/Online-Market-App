package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.example.utils.UrlPath.*;

@Controller
@RequestMapping(ADMIN_PROFILE)
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;

    @GetMapping
    public String adminMenuPage() {
        return "admin/adminMenu";
    }

    @GetMapping(VERIFY_AD)
    public String verifyAdPage(Model model) {
        List<ProductDtoRequest> unverifiedProducts = productService.getProductsByStatus(StatusDto.REVIEW);
        model.addAttribute("unverifiedProducts", unverifiedProducts);
        return "admin/verifyAd";
    }

    @GetMapping(ALL_USERS)
    public String allUsersPage() {
        return "admin/allUsers";
    }
}
