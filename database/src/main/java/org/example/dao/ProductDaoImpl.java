package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Product;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductDaoImpl implements Dao<Long, Product> {
    private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final StatusDao statusDao = StatusDao.getInstance();
    private static final CategoryDao categoryDao = CategoryDao.getInstance();

    public static ProductDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void update(Product entity, Session session) {

    }

    @Override
    public Optional<Product> findById(Long id, Session session) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(Session session) {
        return null;
    }

    @Override
    public void deleteById(Long id, Session session) {

    }

    @Override
    public Long save(Product entity, Session session) {
        return null;
    }
}
