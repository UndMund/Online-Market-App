package org.example.dao;

import org.example.entity.PositionUser;
import org.example.entity.User;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getINSTANCE() {
        return INSTANCE;
    }

    private static String FIND_ALL_SQL = """
            SELECT id,
                username,
                position,
                email,
                phone_number,
                password,
                money_balance
            FROM users
            """;
    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static String UPDATE_BY_ID_SQL = """
            UPDATE users
            SET username = ?,
                position = ?,
                email = ?,
                phone_number = ?,
                password = ?,
                money_balance = ?
            WHERE id = ?  
            """;
    private static String SAVE_SQL = """
            INSERT INTO users (username, position, email, phone_number, password) 
            VALUES (?, ?, ?, ?, ?)
            """;
    private static String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    @Override
    public boolean updateById(User user) {
        try (var connection = ConnectionManager.get()) {
            return updateById(user, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateById(User user, Connection connection) {
        try (var statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPosition().name());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getPassword());
            statement.setLong(6, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            User user = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var result = statement.executeQuery();
            while (result.next()) {
                users.add(buildUser(result));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPosition().name());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getPassword());

            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                user.setId(generatedKeys.getLong("id"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                PositionUser.valueOf(resultSet.getString("position")),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                resultSet.getString("password"),
                resultSet.getBigDecimal("money_balance")
        );
    }


}
