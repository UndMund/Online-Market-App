package org.example.service;

import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.statusDto.StatusDto;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductServiceTest {


    @Test
    public void getProductsByCategory() {
        System.out.println(ProductService.getInstance().getProductsByCategory(CategoryDto.LAPTOP));
    }

    @Test
    public void getProductsByStatus() {
        System.out.println(ProductService.getInstance().getProductsByStatus(StatusDto.ON_SALE));
    }
}