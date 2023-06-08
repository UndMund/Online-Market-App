package org.example.mapper.userMap;

import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class DtoRequestToUserMapper implements Mapper<UserDtoRequest, User> {

    @Override
    public User mapFrom(UserDtoRequest object) {
        return User.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .phoneNumber(object.getPhoneNumber())
                .money(object.getMoney())
                .build();
    }
}
