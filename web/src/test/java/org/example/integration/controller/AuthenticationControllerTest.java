package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.integration.IntegrationTestBase;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.UrlPath.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
public class AuthenticationControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    @Test
    public void loginPageTest() throws Exception {
        mockMvc.perform(get(LOGIN))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("authentication/login"));
    }

    @Test
    public void registrationPageTest() throws Exception {
        mockMvc.perform(get(REGISTRATION))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("authentication/registration"));
    }

    @Test
    public void registrationTest() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                        .param("username", "Test")
                        .param("email", "test@test.ru")
                        .param("phoneNumber", "+375447865456")
                        .param("rawPassword", "Test20")
                        .param("position", "User"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(LOGIN)
                );

        var newUser = userRepository.findById(5L);
        assertThat(newUser).isPresent();
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(post(LOGIN)
                        .param("username", "Helena")
                        .param("password", "Helena20"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(MAIN)
                );
    }
}
