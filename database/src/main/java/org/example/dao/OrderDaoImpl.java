package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Order;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDaoImpl implements Dao<Long, Order> {
    private final static OrderDaoImpl INSTANCE = new OrderDaoImpl();
    private final static UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final static ProductDaoImpl PRODUCT_DAO_IMPL = ProductDaoImpl.getInstance();

    @Override
    public void update(Order entity, Session session) {

    }

    @Override
    public Optional<Order> findById(Long id, Session session) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll(Session session) {
        return null;
    }

    @Override
    public void deleteById(Long id, Session session) {

    }

    @Override
    public Long save(Order entity, Session session) {
        return null;
    }
}
