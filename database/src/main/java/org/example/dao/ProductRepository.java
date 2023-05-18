package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.Status;
import org.example.exception.DaoException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.example.entity.QProduct.product;

@Slf4j
public class ProductRepository extends BaseRepository<Long, Product> {
    public ProductRepository(EntityManager entityManager) {
        super(entityManager, Product.class);
    }
    
    public List<Product> findByCategoryAndStatus(Category category, Status status) {
        try {
            log.info("try to find all products by category: " +  category + "and status: " + status);
            var result = new JPAQuery<Product>(entityManager)
                    .select(product)
                    .from(product)
                    .where(product.category().categoryName.eq(category.getCategoryName()),
                            product.status.eq(status))
                    .fetch();
            log.info("successfully return products list where category: " +  category + "and status: " + status);
            return result;
        } catch (Exception e) {
            log.error("error find all products by category: " +  category + "and status: " + status, e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByStatus(Status status) {
        try {
            log.info("try to find all products by status: " + status);
            var result = new JPAQuery<Product>(entityManager)
                    .select(product)
                    .from(product)
                    .where(product.status.eq(status))
                    .fetch();
            log.info("successfully return products list where status: " + status);
            return result;
        } catch (Exception e) {
            log.error("error find all products by status: status: " + status, e);
            throw new DaoException(e);
        }
    }

    public boolean isUnique(String productName, Long userId) {
        try {
            log.info("try to check product unique. productName: " + productName + " userId: " + userId);
            var result = Optional.ofNullable(
                    new JPAQuery<Product>(entityManager)
                            .select(product)
                            .from(product)
                            .where(product.productName.eq(productName),
                                    product.user().id.eq(userId))
                            .fetchOne()
            );
            log.info("successfully check product unique (is product unique?): " + result.isEmpty());
            return result.isEmpty();
        } catch (Exception e) {
            log.error("error check product unique. productName: " + productName + " userId: " + userId, e);
            throw new DaoException(e);
        }
    }
}
