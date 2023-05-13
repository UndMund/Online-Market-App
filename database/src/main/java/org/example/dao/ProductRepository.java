package org.example.dao;

import org.example.entity.Product;

public class ProductRepository extends BaseRepository<Long, Product> {
    private static final ProductRepository INSTANCE = new ProductRepository(Product.class);
    private static final UserRepository userRep = UserRepository.getInstance();
    private static final CategoryRepository categoryRep = CategoryRepository.getInstance();

    public ProductRepository(Class<Product> clazz) {
        super(clazz);
    }

    public static ProductRepository getInstance() {
        return INSTANCE;
    }
}
