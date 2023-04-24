package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.User;
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
public class UserDaoImpl implements Dao<Long, User> {
    private static final PositionDao positionDao = PositionDao.getInstance();
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private static String FIND_ALL_SQL = """
            SELECT id,
                username,         
                email,
                phone_number,
                password,
                money_balance
            FROM users
            """;
    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static String FIND_BY_NAME_SQL = FIND_ALL_SQL + """
            WHERE username = ?           
            """;

    private static String FIND_BY_EMAIL_SQL = FIND_ALL_SQL + """
            WHERE email = ?           
            """;

    private static String FIND_BY_PHONE_NUMBER_SQL = FIND_ALL_SQL + """
            WHERE phone_number = ?           
            """;
    private static String FIND_BY_USERNAME_AND_PASSWORD_SQL = FIND_ALL_SQL + """
            WHERE username = ?
            AND password = ?
            """;
    private static String UPDATE_BY_ID_SQL = """
            UPDATE users
            SET username = ?,
                email = ?,
                phone_number = ?,
                password = ?,
                money_balance = ?
            WHERE id = ?  
            """;

    private static String UPDATE_PASSWORD_BY_ID_SQL = """
            UPDATE users
            SET password = ?
            WHERE id = ?  
            """;
    private static String SAVE_SQL = """
            INSERT INTO users (username, email, phone_number, password) 
            VALUES (?, ?, ?, ?)
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
            throw new DaoException(e);
        }
    }

    public boolean updateById(User user, Connection connection) {
        try (var statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setBigDecimal(5, user.getMoney());
            statement.setLong(6, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updatePasswordById(Long id, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_PASSWORD_BY_ID_SQL)) {
            statement.setString(1, password);
            statement.setLong(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            User user = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByUsernameAndPassword(String userName, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USERNAME_AND_PASSWORD_SQL)) {
            User user = null;
            statement.setString(1, userName);
            statement.setString(2, password);
            var result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
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
    public User save(User user) {
        try (var connection = ConnectionManager.get()) {
            return save(user, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public User save(User user, Connection connection) {
        try (var statement = connection
                .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());

            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next())
                user.setId(generatedKeys.getLong("id"));

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueUsername(String username) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, username);
            var result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueEmail(String email) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            var result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniquePhoneNumber(String phoneNumber) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_PHONE_NUMBER_SQL)) {
            statement.setString(1, phoneNumber);
            var result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet result) throws SQLException {
        return new User(
                result.getLong("id"),
                result.getString("username"),
                positionDao.findPositionsByUserId(
                        result.getLong("id"),
                        result.getStatement().getConnection()),
                result.getString("email"),
                result.getString("phone_number"),
                result.getString("password"),
                result.getBigDecimal("money_balance")
        );
    }
}
