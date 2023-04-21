package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAttributesValidator {
    private static final UserAttributesValidator INSTANCE = new UserAttributesValidator();

    public static UserAttributesValidator getInstance() {
        return INSTANCE;
    }

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PHONE_NUMBER_PATTERN =
            "\\+(375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})";

    private static final String PASSWORD_PATTERN =
            "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,40}";

    public boolean validateEmail(String email) {
        var pattern = Pattern.compile(EMAIL_PATTERN);
        var matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        var pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        var matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        var  pattern = Pattern.compile(PASSWORD_PATTERN);
        var  matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
