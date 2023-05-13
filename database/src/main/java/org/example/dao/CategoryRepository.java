package org.example.dao;

import org.example.entity.Category;

public class CategoryRepository extends BaseRepository<Integer, Category> {
    private static final CategoryRepository INSTANCE = new CategoryRepository(Category.class);
    private CategoryRepository(Class<Category> clazz) {
        super(clazz);
    }
    public static CategoryRepository getInstance() {
        return INSTANCE;
    }
}
