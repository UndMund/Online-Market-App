package org.example.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Status {
    ON_SALE,
    SALES;

    public static Optional<Status> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role))
                .findFirst();
    }
}
