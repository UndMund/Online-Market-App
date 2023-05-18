package org.example.dao;

import lombok.Cleanup;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

@TestInstance(PER_METHOD)
public class UserRepositoryTest {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @BeforeAll
    public static void setUp() throws Exception {
        TestDataImporter.importData(sessionFactory);
        System.err.println("IMPORT!!!");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        sessionFactory.close();
    }

    @Test
    public void findByUsernameAndPassword() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.findByUsernameAndPassword("Nazar", "Nazar17");
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Nazar");

        var result1 = userRep.findByUsernameAndPassword("Oleg", "Nazar17");
        assertThat(result1).isEmpty();

        session.getTransaction().commit();
    }

    @Test
    public void isUniqueUsername() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.isUniqueUsername("Nazar");
        Assertions.assertFalse(result);
        var result1 = userRep.isUniqueUsername("Oleg");
        Assertions.assertTrue(result1);

        session.getTransaction().commit();
    }

    @Test
    public void isUniqueEmail() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.isUniqueEmail("nazar@mail.ru");
        Assertions.assertFalse(result);
        var result1 = userRep.isUniqueUsername("oleg@mail.ru");
        Assertions.assertTrue(result1);

        session.getTransaction().commit();
    }

    @Test
    public void isUniquePhoneNumber() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.isUniquePhoneNumber("+375336328517");
        Assertions.assertFalse(result);
        var result1 = userRep.isUniqueUsername("+375297654309");
        Assertions.assertTrue(result1);

        session.getTransaction().commit();
    }
}