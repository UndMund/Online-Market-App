package org.example.dto.userDto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class UserDtoUpdatePasswordResponse {
    Long id;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,40}",
            message = "Password must have one lowercase and one uppercase letter, one number and contain at least 6 characters")
    String password;
}
