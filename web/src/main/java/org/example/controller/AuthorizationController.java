package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.service.PositionService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.example.utils.UrlPath.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
public class AuthorizationController {
    private final UserService userService;
    private final PositionService positionService;

    @GetMapping(LOGIN)
    public String loginPage() {
        return "authentication/login";
    }

    @GetMapping(REGISTRATION)
    public String registrationPage(Model model,
                                   @ModelAttribute("userReg") UserDtoRegResponse userReg) {
        model.addAttribute("userReg", userReg);
        return "authentication/registration";
    }

    @PostMapping(REGISTRATION)
    public String registration(@ModelAttribute("userReg") @Validated UserDtoRegResponse userReg,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userReg", userReg);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + REGISTRATION;
        }
        userService.create(userReg);
        return "redirect:" + LOGIN;
    }
}
