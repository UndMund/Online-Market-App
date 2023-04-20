package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserDtoResponse {
    String userName;
    List<String> positions;
    String email;
    String phoneNumber;
    String password;
}
