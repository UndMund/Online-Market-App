package org.example.mapper;

import org.example.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    @Mapping(target = "categoryName", source = "categoryDto")
    abstract Category toCategory(String categoryDto);

    public String toCategoryDto(Category category) {
        return category.getCategoryName();
    }
}
