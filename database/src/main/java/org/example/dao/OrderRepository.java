package org.example.dao;

import org.example.entity.Order;

public class OrderRepository extends BaseRepository<Long, Order> {
    private final static OrderRepository INSTANCE = new OrderRepository(Order.class);
    private final static UserRepository userRep = UserRepository.getInstance();
    private final static ProductRepository productRep = ProductRepository.getInstance();

    private OrderRepository(Class<Order> clazz) {
        super(clazz);
    }
}
