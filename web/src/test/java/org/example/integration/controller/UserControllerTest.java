package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.Status;
import org.example.integration.IntegrationTestBase;
import org.example.mapper.ImageMapper;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.service.ImageService;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.UrlPath.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
@WithMockUser(username = "Helena", password = "Helena20", authorities = {"User"})
public class UserControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private static UserDtoRequest sessionUser;
    @MockBean
    private final ImageService imageService;
    @MockBean
    private final ImageMapper imageMapper;

    @BeforeEach
    public void setUp() {
        sessionUser = userService.findById(1L);
    }

    @Test
    public void userMenuPageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/userMenu"))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void aboutUserPageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + ABOUT).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/about"))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void updatePasswordPageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + CHANGE_PASSWORD).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/changePassword"))
                .andExpect(model().attributeExists("userUpdatePassword"))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void newAdvertisePageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + NEW_AD).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/newAd"))
                .andExpect(model().attributeExists("newProduct"))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void myAdvertisesPageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + MY_AD).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/myAds"))
                .andExpect(model().attributeExists("userProducts"))
                .andExpect(model().attribute("userProducts", hasSize(2)))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void allOrdersPageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + ALL_ORDERS).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/myOrders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(1)))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void replenishBalancePageTest() throws Exception {
        mockMvc.perform(get(USER_PROFILE + REPLENISH_BALANCE).sessionAttr("sessionUser", sessionUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/replenishBalance"))
                .andExpect(model().attributeExists("sessionUser"));
    }

    @Test
    public void addNewAdvertiseTest() throws Exception {
        Mockito.doNothing().when(imageService).uploadImage(null);
        Mockito.doReturn("image.png").when(imageMapper).toString(null);

        mockMvc.perform(post(USER_PROFILE + NEW_AD)
                .param("productName", "Phone")
                .param("price", "50")
                .param("description", "descriptionttesttesttestetest")
                .param("category", "Phones and accessories")
                .param("userId", sessionUser.getId().toString())
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl(USER_PROFILE + NEW_AD)
        );

        var newProduct = productRepository.findById(12L);
        assertThat(newProduct).isPresent();
        assertThat(newProduct.get().getStatus()).isEqualTo(Status.REVIEW);
    }

    @Test
    public void changePasswordTest() throws Exception {
        mockMvc.perform(post(USER_PROFILE + CHANGE_PASSWORD)
                .param("oldPassword", "Helena20")
                .param("rawPassword", "Helena25")
                .param("id", sessionUser.getId().toString())
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl(USER_PROFILE + CHANGE_PASSWORD)
        );

        var newPasswordUser = userRepository.findById(sessionUser.getId());
        assertThat(newPasswordUser).isPresent();
        assertThat(passwordEncoder.matches("Helena25", newPasswordUser.get().getPassword())).isTrue();
    }

    @Test
    public void replenishBalanceTest() throws Exception {
        mockMvc.perform(post(USER_PROFILE + REPLENISH_BALANCE).sessionAttr("sessionUser", sessionUser)
                .param("money", "100")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl(USER_PROFILE + REPLENISH_BALANCE)
        );
        var newBalanceUser = userRepository.findById(sessionUser.getId());
        assertThat(newBalanceUser).isPresent();
        assertThat(newBalanceUser.get().getMoney().compareTo(
                        sessionUser.getMoney().add(BigDecimal.valueOf(100L))
                )
        ).isEqualTo(0);
    }
}


