package org.example.mapper;

import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = PositionMapper.class)
public abstract class UserMapper {
    @Autowired
    private PasswordMapper passwordMapper;

    public abstract UserDtoRequest toUserDto(User user);

    @Mapping(target = "password",
            source = "userDto")
    public abstract User toUser(UserDtoRegResponse userDto);
    public abstract User toUser(UserDtoRequest userDto);


    String mapPassword(UserDtoRegResponse userDto) {
        return passwordMapper.encodePassword(userDto.getRawPassword());
    }
}
