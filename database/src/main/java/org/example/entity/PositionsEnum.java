package org.example.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum PositionsEnum {
    ADMIN(1, "Admin"),
    USER(2, "User");

    private final Integer id;
    private final String name;

    PositionsEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Optional<PositionsEnum> find(String positionName) {
        return Arrays.stream(values())
                .filter(it -> it.getName().equals(positionName))
                .findFirst();
    }
}
