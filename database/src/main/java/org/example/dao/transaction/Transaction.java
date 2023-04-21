package org.example.dao.transaction;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.example.dao.UserDaoImpl;
import org.example.dao.UserPositionDao;
import org.example.entity.Position;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

@UtilityClass
public class Transaction {
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserPositionDao userPositionDao = UserPositionDao.getInstance();

    public static User saveUserAndPositions(User user) throws DaoException {
            Connection connection = null;
            try {
                connection = ConnectionManager.get();
                connection.setAutoCommit(false);
                User newUser = userDao.save(user, connection);
                for (Position newPosition : newUser.getPositions()) {
                    userPositionDao.addUserPosition(newUser, newPosition, connection);
                }
                 connection.commit();
                return newUser;
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new DaoException(e);
                    }
                }
                throw new DaoException(e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new DaoException(e);
                    }
                }
            }
    }
}
