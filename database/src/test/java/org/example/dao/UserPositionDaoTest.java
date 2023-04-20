package org.example.dao;

import static org.example.dao.DeleteSql.*;
import static org.junit.Assert.*;

import org.example.entity.*;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserPositionDaoTest {
    public static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    public static final PositionDao positionDao = PositionDao.getInstance();
    public static final UserPositionDao userPositionDao = UserPositionDao.getInstance();

    public static User user = new User(1L,
            "Ale",
            new ArrayList<>(),
            "ale@mail.ru",
            "+4743999483",
            "1234",
            BigDecimal.ONE);

    @Before
    public void setUp() {
        user = userDao.save(user);
    }

    @After
    public void tearDown() {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_USER_POSITION_SQL + DELETE_USERS_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void addUserPosition() {
        assertEquals(0, user.getPositions().size());
        userPositionDao.addUserPosition(user, Position.USER);
        User newUser = userDao.findAll().get(0);
        assertEquals(1, newUser.getPositions().size());
    }

    @Test
    public void deleteUserPosition() {
        assertEquals(0, user.getPositions().size());
        userPositionDao.addUserPosition(user, Position.ADMIN);
        User newUser = userDao.findAll().get(0);
        assertEquals(1, newUser.getPositions().size());
        userPositionDao.deleteUserPosition(user, Position.ADMIN);
        assertEquals(0, user.getPositions().size());
    }
}