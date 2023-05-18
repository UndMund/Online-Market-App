package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;
import org.example.validator.userValidator.UserNameCorrect;

@Value
@Builder
public class UserDtoLoginResponse {
    @UserNameCorrect
    String username;
    String password;
}
