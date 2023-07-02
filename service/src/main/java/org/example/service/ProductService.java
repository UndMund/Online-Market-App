package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.filter.FilterResolver;
import org.example.dto.filter.ProductFilter;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.QProduct;
import org.example.entity.Status;
import org.example.exception.ServiceException;
import org.example.mapper.ProductMapper;
import org.example.mapper.StatusMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.repository.predicates.QPredicates;
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
    private final StatusMapper statusMapper;

    public Slice<ProductDtoRequest> getProductSliceByFilter(int page, int size, ProductFilter productFilter) {
        try {
            var predicate = QPredicates.builder()
                    .add(Status.ON_SALE, QProduct.product.status::eq)
                    .add(productFilter.categoryFilter(), QProduct.product.category.categoryName::eq)
                    .build();

            return productRepository.findAll(predicate, FilterResolver.priceFilterHandler(page, size, productFilter))
                    .map(productMapper::toProductDto);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Slice<ProductDtoRequest> getProductSlice(Pageable pageable) {
        try {
            return productRepository.findAllByStatus(
                            Status.ON_SALE,
                            pageable
                    )
                    .map(productMapper::toProductDto);
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void buyProduct(ProductDtoRequest productDto, UserDtoRequest userDto) {
        try {
            productRepository.findById(productDto.getId())
                    .map(product -> {
                        product.setStatus(Status.SALES);
                        productRepository.flush();
                        return product;
                    });

            BinaryOperator<BigDecimal> minus = BigDecimal::subtract;
            userService.updateBalance(
                    productDto.getPrice(),
                    userDto.getId(),
                    minus
            );
            BinaryOperator<BigDecimal> plus = BigDecimal::add;
            userService.updateBalance(
                    productDto.getPrice(),
                    productDto.getUser().getId(),
                    plus
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void verifyProduct(Long id) throws ServiceException {
        try {
            productRepository.findById(id)
                    .map(product -> {
                        product.setStatus(Status.ON_SALE);
                        return product;
                    });
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            productRepository.findById(id)
                    .map(entity -> {
                        imageService.deleteImage(entity.getImage());
                        productRepository.delete(entity);
                        productRepository.flush();
                        return entity;
                    });
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public ProductDtoRequest findById(Long id) {
        try {
            return productRepository.findById(id)
                    .map(productMapper::toProductDto)
                    .orElseThrow();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
