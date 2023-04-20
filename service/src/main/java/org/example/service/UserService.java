package org.example.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.ProductDaoImpl;
import org.example.dao.UserDaoImpl;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoResponse;
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

    public Optional<UserDtoRequest> login(String username, String password) {
        return userDao.findByUserName()
    }

    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);
        return userEntity.getId();
    }
}
