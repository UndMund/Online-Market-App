package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.userDto.UserDtoRequest;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.example.utils.UrlPath.*;

@Controller
@RequestMapping(ADVERTISE)
@RequiredArgsConstructor
@SessionAttributes({"currentProduct", "sessionUser"})
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String advertisePage(@PathVariable("id") Long id,
                                Model model) {
        model.addAttribute(
                "currentProduct",
                productService.findById(id)
        );
        return "redirect:" + ADVERTISE;
    }

    @GetMapping
    public String buyAdPage(@SessionAttribute("currentProduct") ProductDtoRequest currentProduct,
                            Model model) {
        model.addAttribute("currentProduct", currentProduct);
        return "advertise/advertise";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(BUY)
    public String buyProduct(@SessionAttribute("currentProduct") ProductDtoRequest currentProduct,
                             @SessionAttribute("sessionUser") UserDtoRequest sessionUser,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (sessionUser.getMoney().compareTo(currentProduct.getPrice()) == -1) {
            redirectAttributes.addFlashAttribute("error",
                    "You don't have enough money!");
            return "redirect:" + ADVERTISE;
        }

        productService.buyProduct(currentProduct, sessionUser);
        model.addAttribute(
                "sessionUser",
                userService.findById(sessionUser.getId())
                );
        redirectAttributes.addFlashAttribute("message",
                "Transaction has been approved!");
        return "redirect:" + ADVERTISE;
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(VERIFY_AD + "/{id}")
    public String verifyAd(@PathVariable(value = "id") Long id) {
        productService.verifyProduct(id);
        return "redirect:" + ADMIN_PROFILE + VERIFY_AD;
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(REMOVE + "/{id}")
    public String removeUnverifiedAd(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:" + ADMIN_PROFILE + VERIFY_AD;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(DELETE + "/{id}")
    public String removeUserAd(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:" + USER_PROFILE + MY_AD;
    }
}
