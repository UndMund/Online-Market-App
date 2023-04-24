package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDtoRegResponse {
    String userName;
    String position;
    String email;
    String phoneNumber;
    String password;
}
