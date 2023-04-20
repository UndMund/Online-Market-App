package org.example.dao;

import org.example.entity.Category;
import org.example.exception.DaoException;
import org.example.utils.ConnectionManager;
import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;

import static org.example.dao.DeleteSql.*;
import static org.junit.Assert.*;

public class CategoryDaoTest {
    public static final CategoryDao categoryDao = CategoryDao.getInstance();
    public static Category category1 = new Category(2, "sdf");
    public static Category category2 = new Category(1, "ddd");

    @After
    public void tearDown() throws Exception {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(DELETE_CATEGORY_SQL))
        {
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    public void findAll() {
        assertEquals(0, categoryDao.findAll().size());
        category2 = categoryDao.addCategory(category2);
        category1 = categoryDao.addCategory(category1);
        assertEquals(2, categoryDao.findAll().size());
    }

    @Test
    public void findById() {
        assertEquals(0, categoryDao.findAll().size());
        category2 = categoryDao.addCategory(category2);
        assertEquals(category2, categoryDao.findById(category2.getId()).get());
    }

    @Test
    public void addCategory() {
        assertEquals(0, categoryDao.findAll().size());
        category2 = categoryDao.addCategory(category2);
        assertEquals(1, categoryDao.findAll().size());
    }

    @Test
    public void delete() {
        assertEquals(0, categoryDao.findAll().size());
        category1 = categoryDao.addCategory(category1);
        category2 = categoryDao.addCategory(category2);
        assertEquals(2, categoryDao.findAll().size());
        categoryDao.delete(category2.getId());
        assertEquals(1, categoryDao.findAll().size());
    }
}