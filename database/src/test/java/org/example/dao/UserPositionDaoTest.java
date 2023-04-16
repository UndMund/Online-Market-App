package org.example.dao;

import static org.junit.Assert.*;

import org.example.entity.*;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserPositionDaoTest {
    public static final UserDaoImpl userDao = UserDaoImpl.getINSTANCE();
    public static final PositionDao positionDao = PositionDao.getINSTANCE();
    public static final UserPositionDao userPositionDao = UserPositionDao.getInstance();

    public static User user = new User(1L,
            "Ale",
            new ArrayList<>(),
            "ale@mail.ru",
            "+4743999483",
            "1234",
            BigDecimal.ONE);

    private static Position position = new Position(
            1,
            "ADMIN"
    );

    @Before
    public void setUp() {
        user = userDao.save(user);
        position = positionDao.save(position);
    }

    @After
    public void tearDown() {
        for (User user1 : userDao.findAll()) {
            userDao.delete(user1.getId());
        }
        for (Position position1 : positionDao.findAll()) {
           positionDao.delete(position1.getId());
        }
        String DELETE_SQL = """
                DELETE FROM user_position
                WHERE position_id = ?
                AND user_id = ?
                """;
            try (var connection = ConnectionManager.get();
                 var statement = connection
                         .prepareStatement(DELETE_SQL)) {
                statement.setLong(1, position.getId());
                statement.setLong(2, user.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
    }

    @Test
    public void addUserPosition() {
        assertEquals(0, user.getPositions().size());
        userPositionDao.addUserPosition(user, position);
        User newUser = userDao.findAll().get(0);
        assertEquals(1, newUser.getPositions().size());
    }

    @Test
    public void deleteUserPosition() {
        assertEquals(0, user.getPositions().size());
        userPositionDao.addUserPosition(user, position);
        User newUser = userDao.findAll().get(0);
        assertEquals(1, newUser.getPositions().size());
        userPositionDao.deleteUserPosition(user, position);
        assertEquals(0, user.getPositions().size());
    }
}