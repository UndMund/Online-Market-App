package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoLogResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.service.PositionService;
import org.example.service.UserService;
import org.example.utils.UrlPath;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {
    //как-то ловиь ошибки и кидать на страницу
    //redirect fields
    //ошибка при повторной регистрации
    private final UserService userService;
    private final PositionService positionService;

    @GetMapping(UrlPath.LOGIN)
    public String loginPage(Model model,
                            @ModelAttribute UserDtoLogResponse userLog) {
        model.addAttribute("userLog", userLog);
        return "authentication/login";
    }

    @PostMapping(UrlPath.LOGIN)
    public String login(Model model,
                        @ModelAttribute UserDtoLogResponse userDto) {
        var sessionUser = userService.login(userDto);
        model.addAttribute("sessionUser", sessionUser);
        return "redirect:" + UrlPath.MAIN;
    }

    @GetMapping(UrlPath.REGISTRATION)
    public String registrationPage(Model model,
                                   @ModelAttribute UserDtoRegResponse userReg) {
        model.addAttribute("userReg", userReg);
        model.addAttribute("positions", positionService.findAll());
        return "authentication/registration";
    }

    @PostMapping(UrlPath.REGISTRATION)
    public String registration(Model model,
                               @ModelAttribute UserDtoRegResponse userReg,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userReg", userReg);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:" + UrlPath.REGISTRATION;
        }
        var sessionUser = userService.create(userReg);
        model.addAttribute("sessionUser", sessionUser);
        return "redirect:" + UrlPath.MAIN;
    }
}
