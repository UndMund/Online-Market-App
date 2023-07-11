package org.example.configuration;

import jakarta.servlet.http.HttpSession;
import org.example.listener.AuthSuccessApplicationListener;
import org.example.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.example.utils.UrlPath.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher(ADMIN_PROFILE + "/**")).hasAuthority("Admin")
                        .requestMatchers(ADVERTISE + VERIFY_AD,
                                ADVERTISE + REMOVE).hasAuthority("Admin")
                        .requestMatchers(antMatcher(USER_PROFILE + "/**")).authenticated()
                        .requestMatchers(LOGIN,
                                REGISTRATION,
                                ADVERTISE,
                                MAIN).permitAll()
                        .requestMatchers(antMatcher(ADVERTISE + "/{\\d}"),
                                antMatcher(MAIN + "{\\d}"),
                                antMatcher(LOAD_IMAGE + "/{\\d}")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage(LOGIN)
                        .defaultSuccessUrl(MAIN)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl(LOGOUT)
                        .logoutSuccessUrl(LOGIN)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationListener applicationListener(HttpSession httpSession, UserService userService) {
        return new AuthSuccessApplicationListener(httpSession, userService);
    }
}
