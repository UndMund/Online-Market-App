package org.example.dao;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StatusDao {
    private static final StatusDao INSTANCE = new StatusDao();

    public static StatusDao getInstance() {
        return INSTANCE;
    }



}
