package org.example.dto.statusDto;

import java.util.Arrays;
import java.util.Optional;

public enum StatusDto {
    ON_SALE,
    SALES,
    REVIEW;

    public static Optional<StatusDto> find(String status) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(status))
                .findFirst();
    }
}
