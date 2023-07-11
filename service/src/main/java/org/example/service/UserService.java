package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.exception.DaoException;
import org.example.exception.ServiceException;
import org.example.mapper.PasswordMapper;
import org.example.mapper.PositionMapper;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordMapper passwordMapper;
    private final PositionMapper positionMapper;

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
                        user.setPassword(
                                passwordMapper.encodePassword(userDto.getRawPassword())
                        );
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

    @Transactional
    public void updateUserPosition(Long id, String position) {
        try {
            userRepository.findById(id)
                    .map(user -> {
                        user.setPosition(positionMapper.toPosition(position));
                        userRepository.flush();
                        return user;
                    });
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public UserDtoRequest findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }

    public UserDtoRequest findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }

    public List<UserDtoRequest> findAllUsers() {
        return userRepository.findAllByPosition(
                        positionMapper.toPosition("User")
                ).stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getPosition())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
