package org.example.service;

import org.example.dao.UserRepository;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.userMap.UserDtoMapper;
import org.example.mapper.userMap.UserMapper;
import org.example.utils.HibernateUtil;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.hibernate.Session;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

public class UserService {
    private static UserService INSTANCE;
    private final Validator validator;
    private final Session session;
    private UserRepository userRep;

    private UserService() {
        session = HibernateUtil.getSession(HibernateUtil.getSessionFactory());
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    public UserDtoRequest login(UserDtoLoginResponse userDto) throws ServiceException {
        ValidationResult<UserDtoLoginResponse> validationResult = new ValidationResult<>();
        try {
            session.beginTransaction();

            userRep = new UserRepository(session);
            var createUserDtoMap = new UserMapper();

            Set<ConstraintViolation<UserDtoLoginResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var user = userRep.findByUsernameAndPassword(
                            userDto.getUsername(),
                            userDto.getPassword()
                    );

            if (user.isEmpty()) {
                validationResult.add(Error.of("Password is invalid"));
                throw new ServiceException(validationResult.getErrors());
            }

            var result = createUserDtoMap.mapFrom(user.get());

            session.getTransaction().commit();

            return result;
        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public UserDtoRequest create(UserDtoRegResponse userDto) throws ServiceException {
        ValidationResult<UserDtoRegResponse> validationResult = new ValidationResult<>();
        try {
            session.beginTransaction();

            userRep = new UserRepository(session);
            var createUserDtoMap = new UserMapper();
            var createUserMap = new UserDtoMapper();

            Set<ConstraintViolation<UserDtoRegResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var userEntity = createUserMap.mapFrom(userDto);
            userEntity.setMoney(BigDecimal.ZERO);
            userEntity = (userRep.save(userEntity));
            var user = createUserDtoMap.mapFrom(userEntity);

            session.getTransaction().commit();

            return user;
        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            session.getTransaction().rollback();
            throw e;
        }

    }

    public void updatePassword(UserDtoUpdatePasswordResponse userDto) throws ServiceException {
        ValidationResult<UserDtoUpdatePasswordResponse> validationResult = new ValidationResult<>();
        try {
            session.beginTransaction();

            userRep = new UserRepository(session);

            Set<ConstraintViolation<UserDtoUpdatePasswordResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var user = userRep.findById(userDto.getId()).get();
            user.setPassword(userDto.getPassword());
            userRep.update(user);

            session.getTransaction().commit();
        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            session.getTransaction().rollback();
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public boolean isUniqueUserName(String username) {
        try {
            return userRep.isUniqueUsername(username);
        } catch (DaoException e) {
            throw e;
        }
    }

    public boolean isUniqueUserEmail(String email) {
        try {
            return userRep.isUniqueEmail(email);
        } catch (DaoException e) {
            throw e;
        }
    }

    public boolean isUniqueUserPhone(String phoneNumber) {
        try {
            return userRep.isUniquePhoneNumber(phoneNumber);
        } catch (DaoException e) {
            throw e;
        }
    }
}
