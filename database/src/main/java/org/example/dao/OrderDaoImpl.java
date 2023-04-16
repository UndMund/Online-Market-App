package org.example.dao;

import org.example.entity.Order;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements Dao<Long, Order> {
    private final static OrderDaoImpl INSTANCE = new OrderDaoImpl();
    private final static UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    private final static ProductDaoImpl PRODUCT_DAO_IMPL = ProductDaoImpl.getINSTANCE();

    private static String FIND_ALL_SQL = """
            SELECT 
            id, product_id, customer_id
            FROM orders     
            """;

    private static String FIND_ALL_BY_USER_ID_SQL = FIND_ALL_SQL + """
            WHERE customer_id = ?
            """;
    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static String UPDATE_BY_ID_SQL = """
            UPDATE orders
            SET product_id = ?,
                customer_id = ?
            WHERE id = ?
            """;
    private static String SAVE_SQL = """
            INSERT INTO orders(product_id, customer_id) 
            VALUES (?, ?)
            """;
    private static String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public boolean updateById(Order order) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setLong(1, order.getProduct().getId());
            statement.setLong(2, order.getUser().getId());
            statement.setLong(3, order.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Order order = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                order = buildOrder(result);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(FIND_ALL_SQL)) {

            List<Order> orders = new ArrayList<>();
            var result = statement.executeQuery();

            while (result.next()) {
                orders.add(buildOrder(result));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Order> findAllByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            List<Order> orders = new ArrayList<>();
            statement.setLong(1, userId);
            var result = statement.executeQuery();
            while (result.next()) {
                orders.add(buildOrder(result));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Order save(Order order) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getProduct().getId());
            statement.setLong(2, order.getUser().getId());

            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                order.setId(generatedKeys.getLong("id"));
            return order;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Order buildOrder(ResultSet result) throws SQLException {
        return new Order(
                result.getLong("id"),
                PRODUCT_DAO_IMPL.findById(
                                result.getLong("product_id"),
                                result.getStatement().getConnection())
                        .orElse(null),
                userDao.findById(
                                result.getLong("customer_id"),
                                result.getStatement().getConnection())
                        .orElse(null)
        );
    }
}
