package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.example.utils.UrlPath.*;

@Controller
@RequestMapping(ADMIN_PROFILE)
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Admin')")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

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
    public String allUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/allUsers";
    }

    @PostMapping(ALL_USERS + "/{id}/update")
    public String updateUserPosition(@PathVariable("id") Long id) {
        userService.updateUserPosition(id, "Admin");
        return "redirect:" + ADMIN_PROFILE + ALL_USERS;
    }

    @GetMapping(ALL_ORDERS)
    public String allOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/allOrders";
    }
}
