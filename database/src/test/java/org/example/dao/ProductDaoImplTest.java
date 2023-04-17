package org.example.dao;

import junit.framework.TestCase;
import org.example.entity.*;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Before;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.dao.DeleteSql.*;

public class ProductDaoImplTest extends TestCase {

    public static final ProductDaoImpl productDao = ProductDaoImpl.getINSTANCE();
    public static final UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    public static final CategoryDao categoryDao = CategoryDao.getINSTANCE();

    public static User user1 = new User(1L,
            "Ale",
            new ArrayList<>(),
            "ale@mail.ru",
            "+4743999483",
            "1234",
            BigDecimal.ONE);

    public static User user2 = new User(1L,
            "Alex",
            new ArrayList<>(),
            "alex@mail.ru",
            "+47423999483",
            "1234",
            BigDecimal.ONE);

    public static Category category1 = new Category(2, "sdf");
    public static Category category2 = new Category(1, "ddd");

    private static Product product1 = new Product(
            1L,
            "poc",
            BigDecimal.TEN,
            "fkfkfkf",
            Status.ON_SALE,
            category2,
            user2);

    private static Product product2 = new Product(
            1L,
            "poee",
            BigDecimal.TEN,
            "fkfkfkf",
            Status.ON_SALE,
            category1,
            user1);

    @Before
    public void setUp() {
        category2 = categoryDao.addCategory(category2);
        user2 = userDao.save(user2);
        category1 = categoryDao.addCategory(category1);
        user1 = userDao.save(user1);
    }

    @After
    public void tearDown() {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_USERS_SQL + DELETE_PRODUCT_SQL + DELETE_CATEGORY_SQL))
        {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void testUpdateById() {
        Product product = productDao.save(product1);
        product.setName("EEE");
        productDao.updateById(product);
        assertEquals(product.getName(), productDao.findById(product.getId()).get().getName());
    }

    public void testFindById() {
        Product product = productDao.save(product1);
        productDao.save(product2);
        assertEquals(product.getId(), productDao.findById(product.getId()).get().getId());
    }

    public void testFindAll() {
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(2, productDao.findAll().size());
    }

    public void testFindAllByUserId() {
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(1, productDao.findAllByUserId(product1.getUser().getId()).size());
    }

    public void testDelete() {
        assertEquals(0, productDao.findAll().size());
        Long id = productDao.save(product1).getId();
        assertEquals(1, productDao.findAll().size());
        productDao.delete(id);
        assertEquals(0, productDao.findAll().size());
    }

    public void testSave() {
        assertEquals(0, productDao.findAll().size());
        productDao.save(product1);
        assertEquals(1, productDao.findAll().size());
    }
}