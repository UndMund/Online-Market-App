package org.example.mapper.userMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoRequestToUserMapper implements Mapper<UserDtoRequest, User> {
    private static final DtoRequestToUserMapper INSTANCE = new DtoRequestToUserMapper();

    public static DtoRequestToUserMapper getInstance() {
        return INSTANCE;
    }

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
