package org.example.service;

import org.example.entity.Category;
import org.example.mapper.CategoryMapper;
import org.example.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    private Category category1;
    private Category category2;

    @BeforeAll
    void setUp() {
        category1 = new Category();

        category2 = new Category();
    }

    @Test
    void getCategories() {
        List<Category> categoryList = Arrays.asList(category1, category2);
        Mockito.doReturn(categoryList).when(categoryRepository).findAll();

        assertThat(categoryService.getCategories()).hasSize(2);
        verify(categoryMapper, times(2)).toCategoryDto(any(Category.class));
        verify(categoryRepository, times(1)).findAll();
    }
}