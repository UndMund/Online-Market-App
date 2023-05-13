package org.example.mapper.categoryMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.categoryDto.CategoryDtoResponse;
import org.example.entity.Category;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDtoMapper implements Mapper<CategoryDtoResponse, Category> {
    private static final CategoryDtoMapper INSTANCE = new CategoryDtoMapper();
    public static CategoryDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Category mapFrom(CategoryDtoResponse object) {
        return Category.builder()
                .categoryName(object.getCategoryName())
                .build();
    }
}
