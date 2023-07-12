package org.example.service;

import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.entity.Position;
import org.example.entity.User;
import org.example.mapper.PasswordMapper;
import org.example.mapper.PositionMapper;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordMapper passwordMapper;
    @Mock
    private PositionMapper positionMapper;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user2 = new User();
    }

    @Test
    public void createTest() {
        doReturn(user1).when(userRepository).save(any(User.class));
        doReturn(UserDtoRequest.builder().build()).when(userMapper).toUserDto(any(User.class));
        doReturn(new User()).when(userMapper).toUser(any(UserDtoRegResponse.class));

        userService.create(UserDtoRegResponse.builder().build());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toUserDto(any(User.class));
        verify(userMapper, times(1)).toUser(any(UserDtoRegResponse.class));
    }

    @Test
    public void updatePasswordTest() {
        doNothing().when(userRepository).flush();
        doReturn(new String()).when(passwordMapper).encodePassword(null);
        doReturn(Optional.of(user1)).when(userRepository).findById(null);

        userService.updatePassword(UserDtoUpdatePasswordResponse.builder().build());

        verify(userRepository, times(1)).flush();
        verify(userRepository, times(1)).findById(null);
        verify(passwordMapper, times(1)).encodePassword(null);
    }

    @Test
    public void updateBalanceTest() {
        user1.setMoney(BigDecimal.ONE);
        doNothing().when(userRepository).flush();
        doReturn(Optional.of(user1)).when(userRepository).findById(any(Long.class));
        doReturn(UserDtoRequest.builder().build()).when(userMapper).toUserDto(any(User.class));

        BinaryOperator<BigDecimal> sum = BigDecimal::add;
        userService.updateBalance(BigDecimal.ZERO, 3L, sum);

        verify(userRepository, times(1)).flush();
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).toUserDto(any(User.class));
    }

    @Test
    public void updateUserPositionTest() {
        doNothing().when(userRepository).flush();
        doReturn(Optional.of(user1)).when(userRepository).findById(any(Long.class));
        doReturn(new Position()).when(positionMapper).toPosition(eq("User"));

        BinaryOperator<BigDecimal> sum = BigDecimal::add;
        userService.updateUserPosition(1L, "User");

        verify(userRepository, times(1)).flush();
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(positionMapper, times(1)).toPosition(eq("User"));
    }

    @Test
    public void findByIdTest() {
        doReturn(Optional.of(user1)).when(userRepository).findById(any(Long.class));
        doReturn(UserDtoRequest.builder().build()).when(userMapper).toUserDto(eq(user1));

        userService.findById(1L);

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).toUserDto(eq(user1));
    }

    @Test
    public void findByUsernameTest() {
        doReturn(Optional.of(user1)).when(userRepository).findByUsername(any(String.class));
        doReturn(UserDtoRequest.builder().build()).when(userMapper).toUserDto(eq(user1));

        userService.findByUsername("Gleb");

        verify(userRepository, times(1)).findByUsername(any(String.class));
        verify(userMapper, times(1)).toUserDto(eq(user1));
    }

    @Test
    public void findAllUsersTest() {
        List<User> users = Arrays.asList(user1, user2);
        doReturn(users).when(userRepository).findAllByPosition(any(Position.class));
        doReturn(new Position()).when(positionMapper).toPosition(eq("User"));
        doReturn(UserDtoRequest.builder().build()).when(userMapper).toUserDto(any(User.class));

        userService.findAllUsers();

        verify(userRepository, times(1)).findAllByPosition(any(Position.class));
        verify(positionMapper, times(1)).toPosition(eq("User"));
        verify(userMapper, times(2)).toUserDto(any(User.class));
    }

}