package org.example.dao;

import lombok.Cleanup;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class UserRepositoryTest {
    private final UserRepository userRep = UserRepository.getInstance();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Before
    public void setUp() throws Exception {
        TestDataImporter.importData(sessionFactory);
        System.err.println("IMPORT!!!");
    }

    @After
    public void tearDown() throws Exception {
        sessionFactory.close();
    }

    @Test
    public void findByUsernameAndPassword() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.findByUsernameAndPassword("Nazar", "Nazar17", session);
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Nazar");

        var result1 = userRep.findByUsernameAndPassword("Oleg", "Nazar17", session);
        assertThat(result1).isEmpty();

        session.getTransaction().commit();
    }

    @Test
    public void isUniqueUsername() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.isUniqueUsername("Nazar", session);
        assertFalse(result);
        var result1 = userRep.isUniqueUsername("Oleg", session);
        assertTrue(result1);

        session.getTransaction().commit();
    }

    @Test
    public void isUniqueEmail() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.isUniqueEmail("nazar@mail.ru", session);
        assertFalse(result);
        var result1 = userRep.isUniqueUsername("oleg@mail.ru", session);
        assertTrue(result1);

        session.getTransaction().commit();
    }

    @Test
    public void isUniquePhoneNumber() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.isUniquePhoneNumber("+375336328517", session);
        assertFalse(result);
        var result1 = userRep.isUniqueUsername("+375297654309", session);
        assertTrue(result1);

        session.getTransaction().commit();
    }
}