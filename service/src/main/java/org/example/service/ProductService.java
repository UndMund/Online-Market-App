package org.example.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.categoryMap.CategoryDtoMapper;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.mapper.productMap.ProductDtoMapper;
import org.example.mapper.productMap.ProductMapper;
import org.example.mapper.statusMap.StatusMapper;
import org.example.mapper.userMap.UserMapper;
import org.example.repository.ProductRepository;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final Validator validator;
    private final ProductRepository productRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final CategoryMapper categoryMapper;
    private final StatusMapper statusMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final ProductDtoMapper productDtoMapper;

    public Slice<ProductDtoRequest> getSliceByCategory(Pageable pageable, CategoryDto categoryDto) {
        return productRepository.findAllByCategory(
                categoryDtoMapper.mapFrom(categoryDto),
                pageable
        ).map(productMapper::mapFrom);
    }

    public List<ProductDtoRequest> getProductsByCategory(CategoryDto categoryDto) {
        try {

            List<ProductDtoRequest> result = productRepository.findAllByCategoryAndStatus(
                            categoryDtoMapper.mapFrom(categoryDto),
                            Status.ON_SALE
                    )
                    .stream()
                    .map(productMapper::mapFrom)
                    .toList();


            return result;
        } catch (DaoException e) {
            var validationResult = new ValidationResult<>();
            validationResult.add(Error.of("Server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        }
    }

    public List<ProductDtoRequest> getProductsByStatus(StatusDto status) {
        try {


            List<ProductDtoRequest> result =
                    productRepository.findAllByStatus(Status.ON_SALE)
                            .stream()
                            .map(productMapper::mapFrom)
                            .toList();


            return result;
        } catch (DaoException e) {
            var validationResult = new ValidationResult<>();
            validationResult.add(Error.of("Server error, please try again later"));

            throw new ServiceException(validationResult.getErrors());
        }
    }

    @Transactional
    public void createProduct(ProductDtoCreateResponse productDto) {
        ValidationResult<ProductDtoCreateResponse> validationResult = new ValidationResult<>();
        try {

            Set<ConstraintViolation<ProductDtoCreateResponse>> validates = validator.validate(productDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var productEntity = productDtoMapper.mapFrom(productDto);
            productEntity.setStatus(Status.REVIEW);

            productRepository.save(productEntity);

        } catch (DaoException e) {
            validationResult.add(Error.of("Server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        }
    }

    public boolean isUniqueProduct(ProductDtoCreateResponse productDto) {
        try {
            return productRepository.findByProductNameAndUserId(
                    productDto.getName(),
                    productDto.getUser().id()
            ).isEmpty();
        } catch (DaoException e) {
            throw e;
        }
    }
}
