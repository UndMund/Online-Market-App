package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Position;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao {
    private static final CategoryDao INSTANCE = new CategoryDao();

    private CategoryDao() {
    }

    public static CategoryDao getINSTANCE() {
        return INSTANCE;
    }

    private static String FIND_ALL_SQL = """
            SELECT id, category_name         
            FROM category
            """;

    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static String SAVE_SQL = """
            INSERT INTO category (category_name)
            VALUES (?)
            """;

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var result = statement.executeQuery();
            while (result.next()) {
                categories.add(buildCategory(result));
            }
            return categories;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Category> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Category> findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Category category= null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                category = buildCategory(result);
            }
            return Optional.ofNullable(category);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean addCategory(Category category) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL)) {
            statement.setString(1, category.getCategoryName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Category buildCategory(ResultSet result) throws SQLException {
        return new Category(
                result.getInt("id"),
                result.getString("category_name")
        );
    }

}
