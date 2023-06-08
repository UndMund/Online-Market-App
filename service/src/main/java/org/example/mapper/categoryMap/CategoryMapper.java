package org.example.mapper.categoryMap;

import org.example.dto.categoryDto.CategoryDto;
import org.example.entity.Category;
import org.example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    @Override
    public CategoryDto mapFrom(Category object) {
        return CategoryDto.builder()
                .id(object.getId())
                .categoryName(object.getCategoryName())
                .build();
    }
}
