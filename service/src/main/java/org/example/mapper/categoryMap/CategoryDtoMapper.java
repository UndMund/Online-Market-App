package org.example.mapper.categoryMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.categoryDto.CategoryDto;
import org.example.entity.Category;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDtoMapper implements Mapper<CategoryDto, Category> {
    private static final CategoryDtoMapper INSTANCE = new CategoryDtoMapper();

    public static CategoryDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Category mapFrom(CategoryDto object) {
        return null;//Category.find(object.getName()).get();
    }
}
