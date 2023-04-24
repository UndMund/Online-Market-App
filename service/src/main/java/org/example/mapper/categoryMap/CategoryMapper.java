package org.example.mapper.categoryMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.categoryDto.CategoryDto;
import org.example.entity.Category;
import org.example.mapper.Mapper;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    private static final CategoryMapper INSTANCE = new CategoryMapper();

    public static CategoryMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public CategoryDto mapFrom(Category object) {
        return new CategoryDto(object.getCategoryName());
    }
}
