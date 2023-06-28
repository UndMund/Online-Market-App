package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.Status;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.CategoryMapper;
import org.example.mapper.ProductMapper;
import org.example.mapper.StatusMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final UserService userService;
    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final StatusMapper statusMapper;

    public Slice<ProductDtoRequest> getSliceByCategory(Pageable pageable, String category) {
        return productRepository.findAllByCategory(
                        categoryMapper.toCategory(category),
                        pageable)
                .map(productMapper::toProductDto);
    }

    public List<ProductDtoRequest> getProductsByCategory(String category) {
        try {
            return productRepository.findAllByCategoryAndStatus(
                            categoryMapper.toCategory(category),
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
            return productRepository.findAllByStatus(
                            statusMapper.toStatus(status)
                    )
                    .stream()
                    .map(productMapper::toProductDto)
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<ProductDtoRequest> getUserProductsOnSale(Long userId) {
        try {
            return productRepository.findAllByUserAndStatus(
                            userRepository.findById(userId).orElseThrow(),
                            Status.ON_SALE
                    )
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
                    .map(productDtoCreate -> {
                        imageService.uploadImage(productDtoCreate.getImage());
                        return productMapper.toProduct(productDtoCreate);
                    })
                    .map(product -> {
                        product.setStatus(Status.REVIEW);
                        product.setCategory(
                                categoryRepository
                                        .findByCategoryName(productDto.getCategory())
                                        .orElseThrow()
                        );
                        product.setUser(
                                userRepository.findById(productDto.getUserId())
                                        .orElseThrow()
                        );
                        return product;
                    })
                    .map(productRepository::save);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void buyProduct(ProductDtoRequest productDto, UserDtoRequest userDto) {
        productRepository.findById(productDto.getId())
                .map(product -> {
                    product.setStatus(Status.SALES);
                    productRepository.flush();
                    return product;
                });

        BinaryOperator<BigDecimal> minus = BigDecimal::subtract;
        userService.updateBalance(productDto.getPrice(), userDto.getId(), minus);
    }

    @Transactional
    public void verifyProduct(Long id) throws ServiceException {
        try {
            productRepository.findById(id)
                    .map(product -> {
                        product.setStatus(Status.ON_SALE);
                        return product;
                    });
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public ProductDtoRequest findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductDto)
                .orElseThrow();
    }
}
