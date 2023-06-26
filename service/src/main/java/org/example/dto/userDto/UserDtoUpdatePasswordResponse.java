package org.example.dto.userDto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;
import org.example.validator.userValidator.OldPasswordCorrect;


@Value
@Builder
@OldPasswordCorrect
public class UserDtoUpdatePasswordResponse {
    Long id;
    String oldPassword;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,40}",
            message = "Password must have one lowercase and one uppercase letter, one number and contain at least 6 characters")
    String newPassword;
}
