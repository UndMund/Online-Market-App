package org.example.integration.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.integration.annotation.IntegrationTest;
import org.example.mapper.CategoryMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@IntegrationTest
public class ProductServiceTest {
    private final ProductService productService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private String categoryDto;

    @BeforeEach
    void setUp() {
         categoryDto = categoryMapper.toCategoryDto(categoryRepository.findById(1).get());
    }

    @Test
    void checkPageableCategoryFilterTest() {

        Pageable pageable = PageRequest.of(0, 4);
        Slice<ProductDtoRequest> slice = productService.getSliceByCategory(pageable, categoryDto);
        assertFalse(slice.isEmpty());
        slice.forEach(System.out::println);
        assertThat(slice).hasSize(4);
    }

    @Test
    void checkPageableDynamicSortTest() {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("price"));
        Slice<ProductDtoRequest> slice = productService.getSliceByCategory(pageable, categoryDto);

        assertFalse(slice.isEmpty());
        assertThat(slice).hasSize(4);
    }

    @Test
    void checkPageable() {
        Pageable pageable0 = PageRequest.of(0, 3);

        Slice<ProductDtoRequest> slice = productService.getSliceByCategory(pageable0, categoryDto);
        assertThat(slice).hasSize(3);
        assertTrue(slice.hasNext());

        slice = productService.getSliceByCategory(slice.nextPageable(), categoryDto);

        assertThat(slice).hasSize(1);
        assertFalse(slice.hasNext());
    }
}
