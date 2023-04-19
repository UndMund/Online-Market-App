package org.example.mapper;

import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserDtoMapper implements Mapper<User, UserDtoRequest> {
    private static final CreateUserDtoMapper INSTANCE = new CreateUserDtoMapper();

    @Override
    public UserDtoRequest mapFrom(User object) {
        return UserDtoRequest.builder()
                .id(object.getId())
                .userName(object.getUserName())
                .positions(object.getPositions())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .money(object.getMoney())
                .build();
    }

    public static CreateUserDtoMapper getInstance() {
        return INSTANCE;
    }
}
