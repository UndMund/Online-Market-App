package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.userMap.UserDtoMapper;
import org.example.mapper.userMap.UserMapper;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class UserService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDtoMapper userDtoMapper;


    public UserDtoRequest login(UserDtoLoginResponse userDto) throws ServiceException {
        ValidationResult<UserDtoLoginResponse> validationResult = new ValidationResult<>();
        try {

            Set<ConstraintViolation<UserDtoLoginResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var user = userRepository.findByUsernameAndPassword(
                            userDto.getUsername(),
                            userDto.getPassword()
                    );

            if (user.isEmpty()) {
                validationResult.add(Error.of("Password is invalid"));
                throw new ServiceException(validationResult.getErrors());
            }

            var result = userMapper.mapFrom(user.get());

            return result;
        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            throw e;
        }
    }

    public UserDtoRequest create(UserDtoRegResponse userDto) throws ServiceException {
        ValidationResult<UserDtoRegResponse> validationResult = new ValidationResult<>();
        try {

            Set<ConstraintViolation<UserDtoRegResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var userEntity = userDtoMapper.mapFrom(userDto);
            userEntity.setMoney(BigDecimal.ZERO);
            userEntity = (userRepository.save(userEntity));
            var user = userMapper.mapFrom(userEntity);


            return user;
        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            throw e;
        }

    }

    public void updatePassword(UserDtoUpdatePasswordResponse userDto) throws ServiceException {
        ValidationResult<UserDtoUpdatePasswordResponse> validationResult = new ValidationResult<>();
        try {

            Set<ConstraintViolation<UserDtoUpdatePasswordResponse>> validates = validator.validate(userDto);
            if (!validates.isEmpty()) {
                validationResult.setValidationErrors(validates);
                throw new ServiceException(validationResult.getErrors());
            }

            var user = userRepository.findById(userDto.getId()).get();
            user.setPassword(userDto.getPassword());

        } catch (DaoException e) {
            validationResult.add(Error.of("server error, please try again later"));
            throw new ServiceException(validationResult.getErrors());
        } catch (ServiceException e) {
            throw e;
        }
    }

    public boolean isUniqueUserName(String username) {
        try {
            return userRepository.findByUsername(username).isEmpty();
        } catch (DaoException e) {
            throw e;
        }
    }

    public boolean isUniqueUserEmail(String email) {
        try {
            return userRepository.findByEmail(email).isEmpty();
        } catch (DaoException e) {
            throw e;
        }
    }

    public boolean isUniqueUserPhone(String phoneNumber) {
        try {
            return userRepository.findByPhoneNumber(phoneNumber).isEmpty();
        } catch (DaoException e) {
            throw e;
        }
    }
}
