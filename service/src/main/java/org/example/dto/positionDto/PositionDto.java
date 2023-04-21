package org.example.dto.positionDto;


import java.util.Arrays;
import java.util.Optional;

public enum PositionDto {
    USER,
    ADMIN;

    public static Optional<PositionDto> find(String position) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(position))
                .findFirst();
    }
}
