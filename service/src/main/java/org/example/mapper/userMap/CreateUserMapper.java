package org.example.mapper.userMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.entity.Position;
import org.example.entity.User;
import org.example.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<UserDtoRegResponse, User> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserDtoRegResponse object) {
        List<Position> positions = new ArrayList<>();
        if (object.getPosition().equals("ADMIN")) {
            positions.add(Position.USER);
            positions.add(Position.ADMIN);
        } else {
            positions.add(Position.USER);
        }

        return User.builder()
                .username(object.getUserName())
                .positions(positions)
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
