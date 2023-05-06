package org.example.dto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum CategoryDto {
    PHONE(1, "Телефоны и аксессуары"),
    LAPTOP(2, "Ноутбуки и аксессуары"),
    BIKE(3, "Мототехника"),
    MUSICAL_INSTRUMENTS(4, "Музыкальные инструменты");

    private final Integer id;
    private final String name;

    public static Optional<CategoryDto> find(String category) {
        return Arrays.stream(values())
                .filter(it -> it.getName().equals(category))
                .findFirst();
    }
}