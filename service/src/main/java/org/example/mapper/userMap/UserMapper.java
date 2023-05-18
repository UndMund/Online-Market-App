package org.example.mapper.userMap;

import lombok.RequiredArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.example.mapper.Mapper;

@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDtoRequest> {
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
}
