package org.example.dao;

import org.example.entity.Position;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.DeleteSql.*;
import static org.junit.Assert.*;

public class PositionDaoTest {
    public static final UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    public static final PositionDao positionDao = PositionDao.getINSTANCE();
    public static final UserPositionDao userPositionDao = UserPositionDao.getINSTANCE();

    private static User user1 = new User(
            1L,
            "Alex",
            new ArrayList<>(),
            "alex@mail.ru",
            "+47423999483",
            "1234",
            BigDecimal.ONE);

    @Before
    public void setUp() throws Exception {
        user1 = userDao.save(user1);
    }

    @After
    public void tearDown() throws Exception {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_USER_POSITION_SQL + DELETE_USERS_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void findById() {
        try (var connection = ConnectionManager.get()) {
            assertEquals(Position.ADMIN, positionDao.findById(1, connection).get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void findPositionsByUserId() {
        userPositionDao.addUserPosition(user1, Position.ADMIN);
        userPositionDao.addUserPosition(user1, Position.USER);
        try (var connection = ConnectionManager.get()) {
             List<Position> positions = positionDao.findPositionsByUserId(user1.getId(), connection);
             assertTrue(positions.contains(Position.USER));
             assertTrue(positions.contains(Position.ADMIN));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}