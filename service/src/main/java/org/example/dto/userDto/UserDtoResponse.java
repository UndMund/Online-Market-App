package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserDtoResponse {
    String userName;
    String position;
    String email;
    String phoneNumber;
    String password;
}
