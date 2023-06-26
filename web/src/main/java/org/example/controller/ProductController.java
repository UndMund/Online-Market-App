package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.example.utils.UrlPath.*;

@Controller
@RequestMapping(ADVERTISE)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/buy/{id}")
    public String buyAdPage(@PathVariable("id") Long id,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "product",
                productService.findById(id)
        );
        return "advertise/buy";
    }

    @PostMapping()
    public String buyProduct() {
        return "";
    }

    @PostMapping(VERIFY_AD)
    public String verifyAd(@RequestParam("id") Long id) {
        productService.verifyProduct(id);
        return "redirect:" + ADMIN_PROFILE + VERIFY_AD;
    }

    @PostMapping(REMOVE)
    public String removeUnverifiedAd(@RequestParam("id") Long id) {
        productService.delete(id);
        return "redirect:" + ADMIN_PROFILE + VERIFY_AD;
    }

    @PostMapping(DELETE)
    public String removeUserAd(@RequestParam("id") Long id) {
        productService.delete(id);
        return "redirect:" + USER_PROFILE + MY_AD;
    }
}
