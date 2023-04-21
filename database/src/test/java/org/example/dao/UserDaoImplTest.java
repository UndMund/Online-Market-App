package org.example.dao;

import junit.framework.TestCase;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.jupiter.api.AfterAll;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.dao.DeleteSql.*;


public class UserDaoImplTest extends TestCase {
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
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

    @AfterAll
    public void tearDown() {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_USERS_SQL))
        {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void testUpdateById() {
        User user = userDao.save(user1);
        user.setUserName("rrr");
        userDao.updateById(user);
        assertEquals(user.getUserName(), userDao.findById(user.getId()).get().getUserName());
    }

    public void testFindById() {
        User user = userDao.save(user1);
        Long id = user.getId();

        assertEquals(user.getId(), userDao.findById(id).get().getId());
    }

    public void testFindByUsernameAndPassword() {
        User user = userDao.save(user1);
        String userName = user.getUserName();
        String password = user.getPassword();

        assertEquals(user.getId(), userDao.findByUsernameAndPassword(userName, password).get().getId());
    }

    public void testFindAll() {
        assertEquals(0, userDao.findAll().size());
        userDao.save(user1);
        userDao.save(user2);
        assertEquals(2, userDao.findAll().size());
    }

    public void testDelete() {
        assertEquals(0, userDao.findAll().size());
        Long id = userDao.save(user1).getId();
        assertEquals(1, userDao.findAll().size());
        userDao.delete(id);
        assertEquals(0, userDao.findAll().size());
    }

    public void testSave() {
        assertEquals(0, userDao.findAll().size());
        userDao.save(user1);
        assertEquals(1, userDao.findAll().size());
    }
}