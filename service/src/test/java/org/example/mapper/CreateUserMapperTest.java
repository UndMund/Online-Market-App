package org.example.mapper;

import org.example.dto.userDto.UserDtoRegResponse;
import org.example.entity.User;
import org.example.mapper.userMap.CreateUserMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateUserMapperTest {
    private UserDtoRegResponse userDtoRegResponse;

    @Before
    public void setUp() throws Exception {
        List<String> positions = new ArrayList<>();
        positions.add("ADMIN");
        userDtoRegResponse = UserDtoRegResponse.builder()
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
        User user = CreateUserMapper.getInstance().mapFrom(userDtoRegResponse);
        assertEquals(userDtoRegResponse.getUserName(), user.getUsername());
        assertEquals(userDtoRegResponse.getPosition(), user.getPositions().get(0).toString());
        assertEquals(userDtoRegResponse.getEmail(), user.getEmail());
        assertEquals(userDtoRegResponse.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDtoRegResponse.getPassword(), user.getPassword());
    }
}