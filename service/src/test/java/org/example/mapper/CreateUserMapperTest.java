package org.example.mapper;

import org.example.dto.userDto.UserDtoResponse;
import org.example.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateUserMapperTest {
    private UserDtoResponse userDtoResponse;

    @Before
    public void setUp() throws Exception {
        List<String> positions = new ArrayList<>();
        positions.add("ADMIN");
        userDtoResponse = UserDtoResponse.builder()
                .userName("Oleg")
                .position("ADMIN")
                .email("oleg@mail.ru")
                .phoneNumber("+274339987654")
                .password("12345yom")
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mapFrom() {
        User user = CreateUserMapper.getInstance().mapFrom(userDtoResponse);
        assertEquals(userDtoResponse.getUserName(), user.getUserName());
        assertEquals(userDtoResponse.getPosition(), user.getPositions().get(0).toString());
        assertEquals(userDtoResponse.getEmail(), user.getEmail());
        assertEquals(userDtoResponse.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDtoResponse.getPassword(), user.getPassword());
    }
}