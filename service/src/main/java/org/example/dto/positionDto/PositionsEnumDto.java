package org.example.dto.positionDto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
@Getter
public enum PositionsEnumDto {
        ADMIN(1, "Admin"),
        USER(2, "User");

        private final Integer id;
        private final String name;

        PositionsEnumDto(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Optional<PositionsEnumDto> find(String position) {
            return Arrays.stream(values())
                    .filter(it -> it.name().equals(position))
                    .findFirst();
        }

}
