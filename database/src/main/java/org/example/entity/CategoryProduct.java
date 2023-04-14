package org.example.entity;

public enum CategoryProduct {
    PHONE("Телефоны и аксессуары"),
    LAPTOP("Ноутбуки и аксессуары"),
    BIKE("Мототехника"),
    MUSICAL_INSTRUMENTS("Музыкальные инструменты");

    private String description;

    public String getDescription() {
        return description;
    }

    CategoryProduct(String description) {
    }


}
