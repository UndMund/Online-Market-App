package org.example.dao;

import org.example.entity.Order;

import javax.persistence.EntityManager;

public class OrderRepository extends BaseRepository<Long, Order> {
    private OrderRepository(EntityManager entityManager) {
        super(entityManager, Order.class);
    }
}
