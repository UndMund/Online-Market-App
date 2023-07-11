package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.integration.IntegrationTestBase;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.UrlPath.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
@WithMockUser(username = "Nazar", password = "Nazar17", authorities = {"Admin"})
public class AdminControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private static UserDtoRequest sessionUser;

    @BeforeEach
    public void setUp() {
        sessionUser = userService.findById(3L);
    }

    @Test
    public void adminMenuPageTest() throws Exception {
        mockMvc.perform(get(ADMIN_PROFILE).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/adminMenu"));
    }

    @Test
    public void verifyAdPageTest() throws Exception {
        mockMvc.perform(get(ADMIN_PROFILE + VERIFY_AD).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/verifyAd"))
                .andExpect(model().attributeExists("unverifiedProducts"))
                .andExpect(model().attribute("unverifiedProducts", hasSize(1)));
    }

    @Test
    public void allUsersPageTest() throws Exception {
        mockMvc.perform(get(ADMIN_PROFILE + ALL_USERS).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/allUsers"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(3)));
    }

    @Test
    public void allOrdersPageTest() throws Exception {
        mockMvc.perform(get(ADMIN_PROFILE + ALL_ORDERS).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/allOrders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(2)));
    }

    @Test
    public void updateUserPositionTest() throws Exception {
        mockMvc.perform(post(ADMIN_PROFILE + ALL_USERS + "/1/update"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(ADMIN_PROFILE + ALL_USERS)
                );

        var newPositionUser = userRepository.findById(1L);
        assertThat(newPositionUser).isPresent();
        assertThat(newPositionUser.get().getPosition().getPositionName()).isEqualTo("Admin");
    }
}
