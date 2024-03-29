package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.CategoryMapper;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@ToString
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<String> getCategories() {
        try {
            return categoryRepository.findAll()
                    .stream()
                    .map(categoryMapper::toCategoryDto)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
