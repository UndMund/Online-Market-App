package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

import static org.example.utils.UrlPath.*;

@Controller
@RequestMapping(USER_PROFILE)
@SessionAttributes("sessionUser")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String userMenuPage() {
        return "user/userMenu";
    }

    @GetMapping(ABOUT)
    public String aboutUserPage() {
        return "user/about";
    }

    @GetMapping(CHANGE_PASSWORD)
    public String updatePasswordPage(@ModelAttribute("userUpdatePassword") UserDtoUpdatePasswordResponse userUpdatePassword,
                                     Model model) {
        model.addAttribute("userUpdatePassword", userUpdatePassword);
        return "user/changePassword";
    }

    @PostMapping(CHANGE_PASSWORD)
    public String updatePassword(@ModelAttribute("userUpdatePassword") @Validated UserDtoUpdatePasswordResponse userUpdatePassword,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdatePassword", userUpdatePassword);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + USER_PROFILE + CHANGE_PASSWORD;
        }
        userService.updatePassword(userUpdatePassword);
        redirectAttributes.addFlashAttribute("message",
                "Password has successfully updated");

        return "redirect:" + USER_PROFILE + CHANGE_PASSWORD;
    }

    @GetMapping(NEW_AD)
    public String newAdvertisePage(Model model,
                                   @ModelAttribute("newProduct") ProductDtoCreateResponse newProduct) {
        model.addAttribute("newProduct", newProduct);
        return "user/newAd";
    }

    @PostMapping(NEW_AD)
    public String addNewAdvertise(Model model,
                                  @ModelAttribute("newProduct") @Validated ProductDtoCreateResponse newProduct,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newProduct", newProduct);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + USER_PROFILE + NEW_AD;
        }
        productService.createProduct(newProduct);
        redirectAttributes.addFlashAttribute("message",
                "Ad has already created. Wait for verification, please");

        return "redirect:" + USER_PROFILE + NEW_AD;
    }

    @GetMapping(MY_AD)
    public String myAdvertisesPage(Model model,
                                   @SessionAttribute("sessionUser") UserDtoRequest sessionUser) {
        model.addAttribute(
                "userProducts",
                productService.getUserProductsOnSale(sessionUser.getId())
        );
        return "user/myAds";
    }

    @GetMapping(ALL_ORDERS)
    public String allOrdersPage(Model model,
                                @SessionAttribute("sessionUser") UserDtoRequest sessionUser) {
        model.addAttribute("orders", orderService.getAllOrdersByUser(sessionUser));
        return "user/myOrders";
    }

    @GetMapping(REPLENISH_BALANCE)
    public String replenishBalancePage() {
        return "user/replenishBalance";
    }

    @PostMapping(REPLENISH_BALANCE)
    public String replenishBalance(@ModelAttribute("money") BigDecimal money,
                                   @SessionAttribute("sessionUser") UserDtoRequest sessionUser,
                                   Model model) {
        BinaryOperator<BigDecimal> sumOperator = BigDecimal::add;
        model.addAttribute("sessionUser",
                userService.updateBalance(money,
                        sessionUser.getId(),
                        sumOperator)
        );
        return "redirect:" + USER_PROFILE + REPLENISH_BALANCE;
    }
}
