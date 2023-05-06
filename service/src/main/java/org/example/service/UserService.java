package org.example.service;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import org.example.dao.UserDaoImpl;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.exception.ValidationException;
import org.example.mapper.userMap.CreateUserDtoMapper;
import org.example.mapper.userMap.CreateUserMapper;
import org.example.utils.HibernateUtil;
import org.example.validator.userValidator.LogInValidator;
import org.example.validator.userValidator.NewPassValidator;
import org.example.validator.userValidator.NewUserValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private final NewUserValidator newUserValidator = NewUserValidator.getInstance();
    private final LogInValidator logInValidator = LogInValidator.getInstance();
    private final NewPassValidator newPassValidator = NewPassValidator.getInstance();
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final CreateUserMapper createUserMap = CreateUserMapper.getInstance();
    private final CreateUserDtoMapper createUserDtoMap = CreateUserDtoMapper.getInstance();
    private static UserService INSTANCE;
    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    public UserDtoRequest login(UserDtoLoginResponse userDto) throws ValidationException {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var validationResult = logInValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var user = userDao.findByUsernameAndPassword(
                                userDto.getUsername(),
                                userDto.getPassword(),
                                session
                        )
                .map(createUserDtoMap::mapFrom);

        validationResult = logInValidator.isValidPassword(user, validationResult);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return user.get();
    }

    public UserDtoRequest create(UserDtoRegResponse userDto) throws ValidationException {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var validationResult = newUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMap.mapFrom(userDto);
        userEntity.setId(userDao.save(userEntity, session));
        //userEntity = Transaction.saveUserAndPositions(userEntity);
        return createUserDtoMap.mapFrom(userEntity);
    }

    public void updatePassword(Long id, String newPassword) throws ValidationException {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var validationResult = newPassValidator.isValid(newPassword);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        //userDao.updatePasswordById(id, newPassword);
    }

    public boolean isUniqueUsername(String username) {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        return userDao.isUniqueUsername(username, session);
    }

    public boolean isUniqueEmail(String email) {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        return userDao.isUniqueEmail(email, session);
    }

    public boolean isUniquePhoneNumber(String phoneNumber) {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        return userDao.isUniquePhoneNumber(phoneNumber, session);
    }
}
