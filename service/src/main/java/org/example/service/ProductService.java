package org.example.service;

import org.example.dao.ProductRepository;
import org.example.dao.UserRepository;
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
import org.example.utils.HibernateUtil;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.hibernate.Session;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

public class ProductService {
    private final Validator validator;
    private final Session session;
    private ProductRepository productRep;
    private static final ProductService INSTANCE = new ProductService();
    public static ProductService getInstance() {
        return INSTANCE;
    }

    private ProductService() {
        session = HibernateUtil.getSession(HibernateUtil.getSessionFactory());
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    public List<ProductDtoRequest> getProductsByCategory(CategoryDto categoryDto) {
        try {
            session.beginTransaction();

            var productRep = new ProductRepository(session);
            var categoryDtoMap = new CategoryDtoMapper();
            var categoryMap = new CategoryMapper();
            var statusMap = new StatusMapper();
            var userMap = new UserMapper();
            var productMap = new ProductMapper(categoryMap, statusMap, userMap);

            List<ProductDtoRequest> result = productRep.findByCategoryAndStatus(
                            categoryDtoMap.mapFrom(categoryDto),
                            Status.ON_SALE
                    )
                    .stream()
                    .map(productMap::mapFrom)
                    .toList();

            session.getTransaction().commit();

            return result;
        } catch (DaoException e) {
            var validationResult = new ValidationResult<>();
            validationResult.add(Error.of("Server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        }
    }

    public List<ProductDtoRequest> getProductsByStatus(StatusDto status) {
        try {
            session.beginTransaction();

            var productRep = new ProductRepository(session);
            var categoryMap = new CategoryMapper();
            var statusMap = new StatusMapper();
            var userMap = new UserMapper();
            var productMap = new ProductMapper(categoryMap, statusMap, userMap);

            List<ProductDtoRequest> result =
                    productRep.findByStatus(Status.ON_SALE)
                    .stream()
                    .map(productMap::mapFrom)
                    .toList();

            session.getTransaction().commit();

            return result;
        } catch (DaoException e) {
            var validationResult = new ValidationResult<>();
            validationResult.add(Error.of("Server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        }
    }

    public void createProduct(ProductDtoCreateResponse productDto) {
        ValidationResult<ProductDtoCreateResponse> validationResult = new ValidationResult<>();
        try {
            session.beginTransaction();

            productRep = new ProductRepository(session);
            var userRep = new UserRepository(session);
            var categoryDtoMap = new CategoryDtoMapper();
            var productDtoMap = new ProductDtoMapper(categoryDtoMap, userRep);

            Set<ConstraintViolation<ProductDtoCreateResponse>> validates = validator.validate(productDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                session.getTransaction().rollback();
                throw new ServiceException(validationResult.getErrors());
            }

            var productEntity = productDtoMap.mapFrom(productDto);
            productEntity.setStatus(Status.REVIEW);

            productRep.save(productEntity);

            session.getTransaction().commit();
        } catch (DaoException e) {
            validationResult.add(Error.of("Server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        }
    }

    public boolean isUniqueProduct(ProductDtoCreateResponse productDto) {
        try {
            return productRep.isUnique(
                    productDto.getName(),
                    productDto.getUser().id()
            );
        } catch (DaoException e) {
            throw e;
        }
    }
}
