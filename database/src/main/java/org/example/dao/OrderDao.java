package org.example.dao;

import org.example.entity.Order;

import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Long, Order> {
    @Override
    public boolean updateById(Order order) {
        return false;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Order save(Order order) {
        return null;
    }
}
