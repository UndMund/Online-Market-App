package org.example.dto.positionDto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
@Getter
public enum Positions {


        ADMIN(1, "Admin"),
        USER(2, "User");

        private final Integer id;
        private final String name;

        Positions(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Optional<Positions> find(String position) {
            return Arrays.stream(values())
                    .filter(it -> it.name().equals(position))
                    .findFirst();
        }

}
