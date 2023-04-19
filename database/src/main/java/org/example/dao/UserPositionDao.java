package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.*;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserPositionDao {
    public static final UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    public static final PositionDao positionDao = PositionDao.getINSTANCE();
    private static final UserPositionDao INSTANCE = new UserPositionDao();

    public static UserPositionDao getINSTANCE() {
        return INSTANCE;
    }

    private static String SAVE_SQL = """
            INSERT INTO user_position(user_id, position_id)
            VALUES (?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM user_position
            WHERE position_id = ? 
            AND   user_id = ?
            """;

    public boolean addUserPosition(User user, Position position) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, position.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public boolean deleteUserPosition(User user, Position position) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_SQL)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, position.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
