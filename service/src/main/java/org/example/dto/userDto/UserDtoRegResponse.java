package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;
import org.example.validator.userValidator.UserEmailUnique;
import org.example.validator.userValidator.UserNameUnique;
import org.example.validator.userValidator.UserPhoneUnique;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
public class UserDtoRegResponse {
    @Size(min = 2, max = 20, message = "Name must be 2-10 characters long")
    @UserNameUnique
    String username;
    @Email(message = "Email must be real")
    @UserEmailUnique
    String email;
    @Pattern(regexp = "\\+(375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})",
            message = "Phone number must be real")
    @UserPhoneUnique
    String phoneNumber;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,40}",
            message = "Password must have one lowercase and one uppercase letter, one number and contain at least 6 characters")
    String password;
    String position;
}
