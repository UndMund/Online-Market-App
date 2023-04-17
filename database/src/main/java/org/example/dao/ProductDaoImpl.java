package org.example.dao;

import org.example.entity.Product;
import org.example.entity.Status;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements Dao<Long, Product> {
    private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    public static final UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    public static final CategoryDao categoryDao = CategoryDao.getINSTANCE();

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getINSTANCE() {
        return INSTANCE;
    }

    private static String FIND_ALL_SQL = """
            SELECT 
            id,
            product_name,
            price,
            description,
            status,
            category_id,
            user_id
            FROM product
            """;

    private static String FIND_ALL_BY_USER_ID_SQL = FIND_ALL_SQL + """
            WHERE user_id = ?
            """;
    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static String UPDATE_BY_ID_SQL = """
            UPDATE product
            SET product_name = ?,
                price = ?,
                description = ?,
                status = ?,
                category_id = ?,
                user_id = ?
            WHERE id = ?
            """;
    private static String SAVE_SQL = """
            INSERT INTO product(product_name, price, description, status, category_id, user_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;

    @Override
    public boolean updateById(Product product) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getStatus().name());
            statement.setInt(5, product.getCategory().getId());
            statement.setLong(6, product.getUser().getId());
            statement.setLong(7, product.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Product> findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Product product = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                product = buildProduct(result);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Product> products = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                products.add(buildProduct(result));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Product> findAllByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            List<Product> products = new ArrayList<>();
            statement.setLong(1, userId);
            var result = statement.executeQuery();
            while (result.next()) {
                products.add(buildProduct(result));
            }
            return products;
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
    public Product save(Product product) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getStatus().name());
            statement.setLong(5, product.getCategory().getId());
            statement.setLong(6, product.getUser().getId());

            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                product.setId(generatedKeys.getLong("id"));
            return product;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Product buildProduct(ResultSet result) throws SQLException {
        return new Product(
                result.getLong("id"),
                result.getString("product_name"),
                result.getBigDecimal("price"),
                result.getString("description"),
                Status.valueOf(result.getString("status")),
                categoryDao.findById(
                                result.getInt("category_id"),
                                result.getStatement().getConnection())
                        .orElse(null),
                userDao.findById(
                                result.getLong("user_id"),
                                result.getStatement().getConnection())
                        .orElse(null)
        );
    }
}
