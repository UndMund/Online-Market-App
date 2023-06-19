package org.example.service;

import jakarta.validation.Validator;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Validator validator;
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

    public UserDtoRequest create(UserDtoRegResponse userDto) throws ServiceException {
        try {
            return Optional.of(userDto)
                    .map(userMapper::toUser)
                    .map(userRepository::save)
                    .map(userMapper::toUserDto)
                    .get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updatePassword(UserDtoUpdatePasswordResponse userDto) throws ServiceException {
        try {

            var user = userRepository.findById(userDto.getId()).get();
            user.setPassword(userDto.getPassword());

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
