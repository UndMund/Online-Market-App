package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.Status;
import org.example.integration.IntegrationTestBase;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.UrlPath.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
public class ProductControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final UserService userService;

    @Test
    public void advertisePageTest() throws Exception {
        mockMvc.perform(get(ADVERTISE + "/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ADVERTISE));
    }

    @Test
    public void buyAdPageTest() throws Exception {
        mockMvc.perform(get(ADVERTISE)
                        .sessionAttr("currentProduct", productService.findById(1L)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("advertise/advertise"))
                .andExpect(model().attributeExists("currentProduct"));
    }

    @WithMockUser(username = "Lina", password = "Lina17", authorities = {"User"})
    @Test
    public void buyProductTest() throws Exception {
        UserDtoRequest sessionUser = userService.findById(4L);
        ProductDtoRequest currentProduct = productService.findById(1L);

        mockMvc.perform(post(ADVERTISE + BUY)
                        .sessionAttr("sessionUser", sessionUser)
                        .sessionAttr("currentProduct", currentProduct))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(ADVERTISE)
                );

        var selledProduct = productRepository.findById(1L);
        assertThat(selledProduct).isPresent();
        assertThat(selledProduct.get().getStatus()).isEqualTo(Status.SALES);

        assertThat(orderRepository.findById(3L)).isPresent();

        var seller = userRepository.findById(currentProduct.getUser().getId());
        assertThat(seller).isPresent();
        assertThat(seller.get().getMoney()
                .compareTo(currentProduct.getUser().getMoney().add(currentProduct.getPrice()))
        ).isEqualTo(0);

        var buyer = userRepository.findById(sessionUser.getId());
        assertThat(buyer).isPresent();
        assertThat(buyer.get().getMoney()
                .compareTo(sessionUser.getMoney().subtract(currentProduct.getPrice()))
        ).isEqualTo(0);
    }

    @WithMockUser(username = "Nazar", password = "Nazar17", authorities = {"Admin"})
    @Test
    public void verifyAdTest() throws Exception {
        mockMvc.perform(post(ADVERTISE + VERIFY_AD + "/4"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(ADMIN_PROFILE + VERIFY_AD)
                ).andDo(print());
        var verifiedProduct = productRepository.findById(4L);
        assertThat(verifiedProduct).isPresent();
        assertThat(verifiedProduct.get().getStatus()).isEqualTo(Status.ON_SALE);

    }
    @WithMockUser(username = "Nazar", password = "Nazar17", authorities = {"Admin"})
    @Test
    public void removeUnverifiedAdTest() throws Exception {
        mockMvc.perform(post(ADVERTISE + REMOVE + "/4"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(ADMIN_PROFILE + VERIFY_AD)
                );
        var verifiedProduct = productRepository.findById(4L);
        assertThat(verifiedProduct).isEmpty();

    }
    @WithMockUser(username = "Helena", password = "Helena20", authorities = {"User"})
    @Test
    public void removeUserAdTest() throws Exception {
        mockMvc.perform(post(ADVERTISE + DELETE + "/3"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(USER_PROFILE + MY_AD)
                );
        var verifiedProduct = productRepository.findById(3L);
        assertThat(verifiedProduct).isEmpty();

    }
}
