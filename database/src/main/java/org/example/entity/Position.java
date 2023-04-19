package org.example.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Position {
    ADMIN(1),
    USER(2);
    private final Integer id;

    Position(Integer id) {
        this.id = id;
    }
    public static Optional<Position> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role))
                .findFirst();
    }
    public Integer getId() {
        return id;
    }
}