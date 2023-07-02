package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoLogResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDtoRequest login(UserDtoLogResponse userDto) throws ServiceException {
        try {
            return userRepository.findByUsernameAndPassword(
                            userDto.getUsername(),
                            userDto.getPassword()
                    ).map(userMapper::toUserDto)
                    .get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public UserDtoRequest create(UserDtoRegResponse userDto) throws ServiceException {
        try {
            return Optional.of(userDto)
                    .map(userMapper::toUser)
                    .map(user -> {
                        user.setMoney(BigDecimal.ZERO);
                        return userRepository.save(user);
                    })
                    .map(userMapper::toUserDto)
                    .get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void updatePassword(UserDtoUpdatePasswordResponse userDto) throws ServiceException {
        try {
            userRepository.findById(userDto.getId())
                    .map(user -> {
                        user.setPassword(userDto.getNewPassword());
                        userRepository.flush();
                        return user;
                    });
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public UserDtoRequest updateBalance(BigDecimal money, Long userId, BinaryOperator<BigDecimal> binaryOperator) {
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        user.setMoney(
                                binaryOperator.apply(
                                        user.getMoney(),
                                        money
                                ));
                        userRepository.flush();
                        return user;
                    }).map(userMapper::toUserDto)
                    .get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public UserDtoRequest findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }
}
