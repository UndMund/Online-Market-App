package org.example.service;

import org.example.dao.CategoryRepository;
import org.example.dto.categoryDto.CategoryDto;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.utils.HibernateUtil;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.hibernate.Session;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

public class CategoryService {
    private final Validator validator;
    private final Session session;
    private CategoryRepository categoryRep;
    private static final CategoryService INSTANCE = new CategoryService();

    private CategoryService() {
        session = HibernateUtil.getSession(HibernateUtil.getSessionFactory());
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    public static CategoryService getInstance() {
        return INSTANCE;
    }

    public List<CategoryDto> getCategories() {
        try {
            session.beginTransaction();

            var categoryRep = new CategoryRepository(session);
            var categoryMap = new CategoryMapper();

            List<CategoryDto> result =
                    categoryRep.findAll()
                            .stream()
                            .map(categoryMap::mapFrom)
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
}
