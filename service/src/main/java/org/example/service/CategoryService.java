package org.example.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.CategoryDao;
import org.example.dto.categoryDto.CategoryDto;
import org.example.mapper.categoryMap.CategoryMapper;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryService {
    private final CategoryDao categoryDao = CategoryDao.getInstance();
    private final CategoryMapper categoryMapper = CategoryMapper.getInstance();
    private static final CategoryService INSTANCE = new CategoryService();

    public static CategoryService getInstance() {
        return INSTANCE;
    }

    public List<CategoryDto> getCategories() {
        return null;//categoryDao.findAll().stream().map(categoryMapper::mapFrom).collect(Collectors.toList());
    }
}
