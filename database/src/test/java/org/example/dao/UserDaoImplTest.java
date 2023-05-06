package org.example.dao;

import lombok.Cleanup;
import org.example.entity.User;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UserDaoImplTest {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Before
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @After
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void update() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByUsernameAndPassword() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void save() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = User.builder()
                .username("Nazar")
                .email("asdf@mail.ru")
                .phoneNumber("+375448767656")
                .password("Nazar17")
                .position(PositionDao.getInstance().findById(
                        Integer.parseInt("1"), session
                ).get())
                .build();
        session.getTransaction().commit();
    }
}