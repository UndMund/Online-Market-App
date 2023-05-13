package org.example.service;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import org.example.dao.CategoryRepository;
import org.example.dto.categoryDto.CategoryDtoRequest;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.utils.HibernateUtil;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryService {
    private final CategoryRepository categoryRep = CategoryRepository.getInstance();
    private final CategoryMapper categoryMapper = CategoryMapper.getInstance();
    private static final CategoryService INSTANCE = new CategoryService();
    public static CategoryService getInstance() {
        return INSTANCE;
    }

    public List<CategoryDtoRequest> getCategories() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        return categoryRep.findAll(session).stream().map(categoryMapper::mapFrom).toList();
    }
}
