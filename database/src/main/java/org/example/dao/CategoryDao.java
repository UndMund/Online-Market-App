package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Category;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CategoryDao implements Dao<Integer, Category>{
    private static final CategoryDao INSTANCE = new CategoryDao();

    public static CategoryDao getInstance() {
        return INSTANCE;
    }

    @Override
    public void update(Category entity, Session session) {

    }

    @Override
    public Optional<Category> findById(Integer id, Session session) {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll(Session session) {
        return null;
    }

    @Override
    public void deleteById(Integer id, Session session) {

    }

    @Override
    public Integer save(Category entity, Session session) {
        return null;
    }
}
