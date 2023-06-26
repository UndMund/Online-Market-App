package org.example.mapper;

import org.example.entity.Category;
import org.springframework.stereotype.Component;
@Component
public class CategoryMapper {
    public Category toCategory(String categoryDto) {
        return Category.builder()
                .categoryName(categoryDto)
                .build();
    }
    public String toCategoryDto(Category category) {
        return category.getCategoryName();
    }
}
