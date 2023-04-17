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
    private static User user2 = new User(
            1L,
            "Ale",
            new ArrayList<>(),
            "ale@mail.ru",
            "+4743999483",
            "1234",
            BigDecimal.ONE);

    private static Position position1 = new Position(
            1,
            "ADMIN"
    );

    private static Position position2 = new Position(
            1,
            "USER"
    );

    @Before
    public void setUp() throws Exception {
        user1 = userDao.save(user1);
        position1 = positionDao.save(position1);
        userPositionDao.addUserPosition(user1, position1);
    }

    @After
    public void tearDown() throws Exception {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_USER_POSITION_SQL + DELETE_USERS_SQL + DELETE_POSITION_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void save() {
        assertEquals(1, positionDao.findAll().size());
        position2 = positionDao.save(position2);
        assertEquals(2, positionDao.findAll().size());
    }

    @Test
    public void findAll() {
        position2 = positionDao.save(position2);
        assertEquals(2, positionDao.findAll().size());
    }

    @Test
    public void findById() {
        assertEquals(1, positionDao.findAll().size());
        position2 = positionDao.save(position2);
        assertEquals(2, positionDao.findAll().size());
        assertEquals(position1, positionDao.findById(position1.getId()).get());
    }

    @Test
    public void delete() {
        assertEquals(1, positionDao.findAll().size());
        position2 = positionDao.save(position2);
        assertEquals(2, positionDao.findAll().size());
        positionDao.delete(position2.getId());
        assertEquals(1, positionDao.findAll().size());
    }

    @Test
    public void findPositionsByUserId() {
        position2 = positionDao.save(position2);
        userPositionDao.addUserPosition(user1, position2);
        try (var connection = ConnectionManager.get()) {
             List<Position> userPositions = positionDao.findPositionsByUserId(user1.getId(), connection);
             assertEquals(userPositions, positionDao.findAll());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}