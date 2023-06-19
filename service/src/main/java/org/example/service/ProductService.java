package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.CategoryMapper;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public Slice<ProductDtoRequest> getSliceByCategory(Pageable pageable, CategoryDto categoryDto) {
        return productRepository.findAllByCategory(
                        categoryMapper.toCategory(categoryDto),
                        pageable)
                .map(productMapper::toProductDto);
    }

    public List<ProductDtoRequest> getProductsByCategory(CategoryDto categoryDto) {
        try {
            return productRepository.findAllByCategoryAndStatus(
                            categoryMapper.toCategory(categoryDto),
                            Status.ON_SALE
                    )
                    .stream()
                    .map(productMapper::toProductDto)
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<ProductDtoRequest> getProductsByStatus(StatusDto status) {
        try {
            return productRepository.findAllByStatus(Status.ON_SALE)
                    .stream()
                    .map(productMapper::toProductDto)
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void createProduct(ProductDtoCreateResponse productDto) {
        try {
            Optional.of(productDto)
                    .map(productMapper::toProduct)
                    .map(product -> {
                        product.setStatus(Status.REVIEW);
                        return product;
                    })
                    .map(productRepository::save);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
