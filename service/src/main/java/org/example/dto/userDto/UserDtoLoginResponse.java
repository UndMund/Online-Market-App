package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDtoLoginResponse {
    String userName;
    String password;
}
