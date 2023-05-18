package org.example.mapper.userMap;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.entity.Position;
import org.example.entity.PositionsEnum;
import org.example.entity.User;
import org.example.mapper.Mapper;

@RequiredArgsConstructor
public class UserDtoMapper implements Mapper<UserDtoRegResponse, User> {

    @Override
    public User mapFrom(UserDtoRegResponse object) {
        PositionsEnum position = PositionsEnum.find(object.getPosition()).get();//
        return User.builder()
                .username(object.getUsername())
                .position(Position.builder()
                        .id(position.getId())
                        .positionName(position.getName())
                        .build())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .password(object.getPassword())
                .build();
    }
}
