package org.example.mapper.userMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.entity.User;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<UserDtoRegResponse, User> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserDtoRegResponse object) {
        return User.builder()
                .username(object.getUsername())
                //.position(PositionsEnum.find(object.getPosition()))
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
