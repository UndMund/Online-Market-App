package org.example.mapper;

import org.example.dto.userDto.UserDtoCreateProductResponse;
import org.example.dto.userDto.UserDtoLogResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface UserMapper {
    UserDtoRequest toUserDto(User user);
    User toUser(UserDtoLogResponse userDto);
    User toUser(UserDtoRegResponse userDto);
    User toUser(UserDtoCreateProductResponse userDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromDto(UserDtoRegResponse userDto, @MappingTarget User user);
}
