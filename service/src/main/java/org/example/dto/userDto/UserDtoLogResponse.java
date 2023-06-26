package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;
import org.example.validator.userValidator.LoginDataCorrect;

@Value
@Builder
@LoginDataCorrect
public class UserDtoLogResponse {
    String username;
    String password;
}
