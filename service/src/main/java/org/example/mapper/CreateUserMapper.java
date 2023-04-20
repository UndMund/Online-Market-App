package org.example.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoResponse;
import org.example.entity.Position;
import org.example.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<UserDtoResponse, User> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserDtoResponse object) {
        return User.builder()
                .userName(object.getUserName())
                .positions(object.getPositions()
                        .stream()
                        .map(position -> Position.find(position).get())
                        .toList())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
