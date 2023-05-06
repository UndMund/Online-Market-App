package org.example.mapper.userMap;

import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.example.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserDtoMapper implements Mapper<User, UserDtoRequest> {
    private static final CreateUserDtoMapper INSTANCE = new CreateUserDtoMapper();

    @Override
    public UserDtoRequest mapFrom(User object) {
        return UserDtoRequest.builder()
                .id(object.getId())
                .username(object.getUsername())
                .position(object.getPosition().getPositionName())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .money(object.getMoney())
                .build();
    }

    public static CreateUserDtoMapper getInstance() {
        return INSTANCE;
    }
}
