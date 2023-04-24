package org.example.mapper.userMap;

import lombok.NoArgsConstructor;
import org.example.dto.positionDto.PositionDto;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.example.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserDtoMapper implements Mapper<User, UserDtoRequest> {
    private static final CreateUserDtoMapper INSTANCE = new CreateUserDtoMapper();

    @Override
    public UserDtoRequest mapFrom(User object) {
        PositionDto positionDto;
        if (object.getPositions().size() == 2) {
            positionDto = PositionDto.ADMIN;

        } else {
            positionDto = PositionDto.USER;
        }

        return UserDtoRequest.builder()
                .id(object.getId())
                .username(object.getUsername())
                .position(positionDto.name())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .money(object.getMoney())
                .build();
    }

    public static CreateUserDtoMapper getInstance() {
        return INSTANCE;
    }
}
