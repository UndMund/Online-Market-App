package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoLogResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.service.PositionService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.example.utils.UrlPath.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
public class AuthorizationController {
    private final UserService userService;
    private final PositionService positionService;

    @GetMapping(LOGIN)
    public String loginPage(Model model,
                            @ModelAttribute("userLog") UserDtoLogResponse userLog) {
        model.addAttribute("userLog", userLog);
        return "authentication/login";
    }

    @PostMapping(LOGIN)
    public String login(@ModelAttribute("userLog") @Validated UserDtoLogResponse userLog,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLog", userLog);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + LOGIN;
        }
        var sessionUser = userService.login(userLog);
        model.addAttribute("sessionUser", sessionUser);
        return "redirect:" + MAIN;
    }

    @GetMapping(REGISTRATION)
    public String registrationPage(Model model,
                                   @ModelAttribute("userReg") UserDtoRegResponse userReg) {
        model.addAttribute("userReg", userReg);
        model.addAttribute("positions", positionService.findAll());
        return "authentication/registration";
    }

    @PostMapping(REGISTRATION)
    public String registration(@ModelAttribute("userReg") @Validated UserDtoRegResponse userReg,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userReg", userReg);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + REGISTRATION;
        }
        var sessionUser = userService.create(userReg);
        model.addAttribute("sessionUser", sessionUser);
        return "redirect:" + MAIN;
    }

    @PostMapping(LOGOUT)
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:" + LOGIN;
    }
}
