package org.example.dto.filter;

import java.util.Arrays;
import java.util.Optional;

public enum PriceFilterEnum {
    Ascending, Descending;

    public static Optional<PriceFilterEnum> find(String status) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(status))
                .findFirst();
    }
}
