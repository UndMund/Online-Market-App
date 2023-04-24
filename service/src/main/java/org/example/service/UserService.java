package org.example.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.UserDaoImpl;
import org.example.dao.transaction.Transaction;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.exception.ValidationException;
import org.example.mapper.userMap.CreateUserDtoMapper;
import org.example.mapper.userMap.CreateUserMapper;
import org.example.validator.userValidator.LogInValidator;
import org.example.validator.userValidator.NewPassValidator;
import org.example.validator.userValidator.NewUserValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final NewUserValidator newUserValidator = NewUserValidator.getInstance();
    private final LogInValidator logInValidator = LogInValidator.getInstance();
    private final NewPassValidator newPassValidator = NewPassValidator.getInstance();
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private final CreateUserMapper createUserMap = CreateUserMapper.getInstance();
    private final CreateUserDtoMapper createUserDtoMap = CreateUserDtoMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public UserDtoRequest login(UserDtoLoginResponse userDto) throws ValidationException {
        var validationResult = logInValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = userDao.findByUsernameAndPassword(userDto.getUserName(), userDto.getPassword())
                .map(createUserDtoMap::mapFrom);

        validationResult = logInValidator.isValidPassword(user, validationResult);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return user.get();
    }

    public UserDtoRequest create(UserDtoRegResponse userDto) throws ValidationException {
        var validationResult = newUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMap.mapFrom(userDto);
        userEntity = Transaction.saveUserAndPositions(userEntity);
        return createUserDtoMap.mapFrom(userEntity);
    }

    public void updatePassword(Long id, String newPassword) throws ValidationException {
        var validationResult = newPassValidator.isValid(newPassword);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        userDao.updatePasswordById(id, newPassword);
    }
}
