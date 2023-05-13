package org.example.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.ProductRepository;
import org.example.dto.categoryDto.CategoryDtoResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.productDto.ProductDtoResponse;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.exception.ValidationException;
import org.example.mapper.categoryMap.CategoryDtoMapper;
import org.example.mapper.productMap.ProductDtoMapper;
import org.example.mapper.productMap.ProductMapper;
import org.example.mapper.statusMap.StatusDtoMapper;
import org.example.validator.productValidator.NewProductValidator;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private static final ProductRepository productRep = ProductRepository.getInstance();
    private static final NewProductValidator productValidator = NewProductValidator.getInstance();
    private static final CategoryDtoMapper categoryMap = CategoryDtoMapper.getInstance();
    private static final ProductMapper productMap = ProductMapper.getInstance();
    private static final ProductDtoMapper productDtoMap = ProductDtoMapper.getInstance();
    private static final StatusDtoMapper statusMap = StatusDtoMapper.getInstance();
    public static ProductService getInstance() {
        return INSTANCE;
    }

    public List<ProductDtoRequest> getProductsByCategory(CategoryDtoResponse categoryDtoResponse) {
        return null;/*productDao.findByCategoryAndStatus(
                categoryMap.mapFrom(categoryDto), Status.ON_SALE)
                .stream()
                .map(productMap::mapFrom)
                .collect(Collectors.toList());*/

    }

    public List<ProductDtoRequest> getProductsByStatus(StatusDto status) {
        return null;/*productDao.findByStatus(
                statusMap.mapFrom(status))
                .stream()
                .map(productMap::mapFrom)
                .collect(Collectors.toList());*/
    }

    public void createProduct(ProductDtoResponse productDto) {
        var validationResult = productValidator.isValid(productDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var newProduct = productDtoMap.mapFrom(productDto);
        newProduct.setStatus(Status.REVIEW);
        /*productDao.save(newProduct);*/
    }

}
