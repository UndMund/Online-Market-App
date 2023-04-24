package org.example.service;

import org.example.dao.UserDaoImpl;
import org.example.dto.positionDto.PositionDto;
import org.example.dto.userDto.UserDtoRequest;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.exception.ValidationException;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void login() {
    }

    @Test
    public void create() {
        List<String> positions = new ArrayList<>();
        positions.add("ADMIN");
        UserDtoRegResponse newUser = UserDtoRegResponse.builder()
                .userName("Nazar")
                .email("zavadskiy.nazar@mail.ru")
                .phoneNumber("+375336328517")
                .position("ADMIN")
                .password("Nazar17")
                .build();
        try {
           UserDtoRequest userDtoRequest =  UserService.getInstance().create(newUser);
            assertTrue(userDao.findById(userDtoRequest.getId()).isPresent());
        } catch (ValidationException e) {
            System.out.println(e.getErrors());
        }
        System.out.println(Arrays.toString(PositionDto.values()));
    }
}