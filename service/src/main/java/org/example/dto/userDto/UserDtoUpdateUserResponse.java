package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDtoUpdateUserResponse {
    String id;
    String username;
    String position;
    String email;
    String phoneNumber;
    String password;
}
