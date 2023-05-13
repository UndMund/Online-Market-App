package org.example.service;

import org.example.dto.statusDto.StatusDto;
import org.junit.Test;

public class ProductServiceTest {


    @Test
    public void getProductsByCategory() {
        //System.out.println(ProductService.getInstance().getProductsByCategory(CategoryDtoResponse.LAPTOP));
    }

    @Test
    public void getProductsByStatus() {
        System.out.println(ProductService.getInstance().getProductsByStatus(StatusDto.ON_SALE));
    }
}