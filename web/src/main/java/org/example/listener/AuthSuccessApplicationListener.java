package org.example.listener;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthSuccessApplicationListener implements
        ApplicationListener<AuthenticationSuccessEvent> {
    private HttpSession httpSession;
    private final UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        var principal = (UserDetails) event.getAuthentication().getPrincipal();
        try {
            var userDto = userService.findByUsername(principal.getUsername());
            log.info("Try to add sessionUser to session {}", userDto);
            httpSession.setAttribute("sessionUser", userDto);
            log.info("Successfully added to session sessionUser {}", userDto);
        } catch (Exception e) {
            log.error("Exception occurred : " + e.getMessage());
        }
    }
}
