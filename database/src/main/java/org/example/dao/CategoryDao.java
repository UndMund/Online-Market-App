package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Category;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CategoryDao {
    private static final CategoryDao INSTANCE = new CategoryDao();

    public static CategoryDao getInstance() {
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

    private static String DELETE_SQL = """
            DELETE FROM category
            WHERE id = ?
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

    public Optional<Category> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Category> findById(Integer id, Connection connection) {
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

    public Category addCategory(Category category) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getCategoryName());
            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                category.setId(generatedKeys.getInt("id"));
            return category;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
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
