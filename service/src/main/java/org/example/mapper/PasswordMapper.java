package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordMapper {
    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {
        return Optional.ofNullable(rawPassword)
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .orElseThrow();
    }
}
