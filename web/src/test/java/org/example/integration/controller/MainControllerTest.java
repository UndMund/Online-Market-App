package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.filter.PriceFilterEnum;
import org.example.dto.filter.ProductFilter;
import org.example.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.utils.UrlPath.MAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
public class MainControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;


    @Test
    public void defaultMainPageTest() throws Exception {
        mockMvc.perform(get(MAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(MAIN + 0));
    }

    @Test
    public void defaultMainPageTestWithAttributes() throws Exception {
        mockMvc.perform(get(MAIN).sessionAttr("sessionPage", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(MAIN + 2));
    }

    @Test
    public void mainPageTesWithPage() throws Exception {
        mockMvc.perform(get(MAIN + 0).sessionAttr("sessionPage", 2))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("main/main"),
                        model().attributeExists("productFilter", "products", "sessionPage", "priceFilterValues")
                );
    }

    @Test
    public void mainPageTestWithAttributes() throws Exception {
        mockMvc.perform(get(MAIN + 0)
                        .sessionAttr("sessionPage", 2)
                        .sessionAttr("productFilter", new ProductFilter("Phones and accessories", PriceFilterEnum.Ascending.name())))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("main/main"),
                        model().attributeExists("productFilter", "products", "sessionPage", "priceFilterValues")
                );
    }

    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get(MAIN + 0))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("main/main"),
                        model().attributeExists("productFilter", "products", "sessionPage", "priceFilterValues")
                );
    }

    @Test
    public void mainPageSelectPageTest() throws Exception {
        mockMvc.perform(post(MAIN + 1))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(MAIN + 1)
                );
    }
}
