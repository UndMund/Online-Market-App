package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDtoLogResponse {
//    @UserNameCorrect
    String username;
    String password;
}
