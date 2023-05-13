package org.example.mapper.categoryMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.categoryDto.CategoryDtoRequest;
import org.example.entity.Category;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper implements Mapper<Category, CategoryDtoRequest> {
    private static final CategoryMapper INSTANCE = new CategoryMapper();

    public static CategoryMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public CategoryDtoRequest mapFrom(Category object) {
        return CategoryDtoRequest.builder()
                .id(object.getId())
                .categoryName(object.getCategoryName())
                .build();
    }
}
