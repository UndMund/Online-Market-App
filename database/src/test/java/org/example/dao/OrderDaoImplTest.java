package org.example.dao;

import org.example.entity.*;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.dao.DeleteSql.*;
import static org.junit.Assert.*;

public class OrderDaoImplTest {

    public static final ProductDaoImpl productDao = ProductDaoImpl.getInstance();
    public static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    public static final CategoryDao categoryDao = CategoryDao.getInstance();
    public static final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();

    private static User user1 = new User(
            1L,
            "Alex",
            new ArrayList<>(),
            "alex@mail.ru",
            "+47423999483",
            "1234",
            BigDecimal.ZERO);
    private static User user2 = new User(
            1L,
            "Ale",
            new ArrayList<>(),
            "ale@mail.ru",
            "+4743999483",
            "1234",
            BigDecimal.ZERO);

    private static User user3 = new User(
            1L,
            "Alena",
            new ArrayList<>(),
            "alena@mail.ru",
            "+43999483",
            "1234",
            BigDecimal.ZERO);

    public static Category category = new Category(2, "sdf");

    private static Product product1 = new Product(
            1L,
            "poc",
            BigDecimal.TEN,
            "fkfkfkf",
            Status.ON_SALE,
            category,
            user1);

    private static Product product2 = new Product(
            1L,
            "poee",
            BigDecimal.TEN,
            "fkfkfkf",
            Status.ON_SALE,
            category,
            user2);

    public static Order order1 = new Order(
            1L,
            product1,
            user2
    );

    public static Order order2 = new Order(
            1L,
            product2,
            user1
    );

    @Before
    public void setUp() throws Exception {
        category = categoryDao.addCategory(category);
        user1 = userDao.save(user1);
        user2 = userDao.save(user2);
        user3 = userDao.save(user3);
        product1 = productDao.save(product1);
        product2 = productDao.save(product2);
    }

    @After
    public void tearDown() throws Exception {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_ORDERS_SQL + DELETE_USERS_SQL + DELETE_PRODUCT_SQL + DELETE_CATEGORY_SQL))
        {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void updateById() {
        assertEquals(0, orderDao.findAll().size());
        order1 = orderDao.save(order1);
        assertEquals(1, orderDao.findAll().size());
        order1.setUser(user3);
        orderDao.updateById(order1);
        assertEquals(order1, orderDao.findAll().get(0));
    }

    @Test
    public void findById() {
        assertEquals(0, orderDao.findAll().size());
        order1 = orderDao.save(order1);
        order2 = orderDao.save(order2);
        assertEquals(order1, orderDao.findById(order1.getId()).get());
    }

    @Test
    public void findAll() {
        assertEquals(0, orderDao.findAll().size());
        orderDao.save(order1);
        orderDao.save(order2);
        assertEquals(2, orderDao.findAll().size());
    }

    @Test
    public void findAllByUserId() {
        assertEquals(0, orderDao.findAll().size());
        order1 = orderDao.save(order1);
        order2 = orderDao.save(order2);
        assertEquals(order1.getId(), orderDao.findAllByCustomerId(user2.getId()).get(0).getId());
    }

    @Test
    public void delete() {
        assertEquals(0, orderDao.findAll().size());
        order1 = orderDao.save(order1);
        assertEquals(1, orderDao.findAll().size());
        orderDao.delete(order1.getId());
        assertEquals(0, orderDao.findAll().size());
    }

    @Test
    public void save() {
        assertEquals(0, orderDao.findAll().size());
        orderDao.save(order1);
        assertEquals(1, orderDao.findAll().size());
    }
}