package org.example.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.example.dao.CategoryRepository;
import org.example.dto.categoryDto.CategoryDto;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@ToString
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryDto> getCategories() {
        try {

            List<CategoryDto> result =
                    categoryRepository.findAll()
                            .stream()
                            .map(categoryMapper::mapFrom)
                            .toList();
            return result;
        } catch (DaoException e) {
            var validationResult = new ValidationResult<>();
            validationResult.add(Error.of("Server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        }
    }
}
