package org.example.mapper.categoryMap;

import org.example.dto.categoryDto.CategoryDto;
import org.example.entity.Category;
import org.example.mapper.Mapper;
public class CategoryDtoMapper implements Mapper<CategoryDto, Category> {
    @Override
    public Category mapFrom(CategoryDto object) {
        return Category.builder()
                .id(object.getId())
                .categoryName(object.getCategoryName())
                .build();
    }
}
