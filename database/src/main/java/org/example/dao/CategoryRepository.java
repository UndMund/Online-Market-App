package org.example.dao;

import org.example.entity.Category;

import javax.persistence.EntityManager;

public class CategoryRepository extends BaseRepository<Integer, Category> {
    public CategoryRepository(EntityManager entityManager) {
        super(entityManager, Category.class);
    }
}
