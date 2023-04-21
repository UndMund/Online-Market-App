package org.example.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.UserDaoImpl;
import org.example.dao.transaction.Transaction;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoResponse;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.ValidationException;
import org.example.mapper.CreateUserDtoMapper;
import org.example.mapper.CreateUserMapper;
import org.example.validator.userValidator.NewUserValidator;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final NewUserValidator newUserValidator = NewUserValidator.getInstance();
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private final CreateUserMapper createUserMap = CreateUserMapper.getInstance();
    private final CreateUserDtoMapper createUserDtoMap = CreateUserDtoMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDtoRequest> login(String username, String password) throws ValidationException {
        return userDao.findByUsernameAndPassword(username, password)
                .map(createUserDtoMap::mapFrom);
    }

    public UserDtoRequest create(UserDtoResponse userDto) throws ValidationException {
        var validationResult = newUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMap.mapFrom(userDto);
        userEntity = Transaction.saveUserAndPositions(userEntity);
        return createUserDtoMap.mapFrom(userEntity);
    }
}
