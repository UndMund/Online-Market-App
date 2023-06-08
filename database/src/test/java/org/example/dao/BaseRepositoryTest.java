package org.example.dao;

import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

@TestInstance(PER_METHOD)
public class BaseRepositoryTest {
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

  /*  @Test
    public void save() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result1 = userRep.findById(5L);
        assertThat(result1).isEmpty();

        var newUser = User.builder()
                .username("Lina")
                .phoneNumber("+375297546743")
                .email("lina@mail.ru")
                .password("Lina16")
                .position(Position.builder()
                        .id(PositionsEnum.USER.getId())
                        .positionName(PositionsEnum.USER.getName())
                        .build())
                .money(BigDecimal.ZERO)
                .build();
        session.save(newUser);

        var result2 = userRep.findById(5L);
        assertThat(result2).isPresent();

        session.getTransaction().commit();
    }

    @Test
    public void delete() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var test = userRep.findById(4L);
        assertThat(test).isEmpty();

        var newUser = User.builder()
                .username("Lina")
                .phoneNumber("+375297546743")
                .email("lina@mail.ru")
                .password("Lina16")
                .position(Position.builder()
                        .id(PositionsEnum.USER.getId())
                        .positionName(PositionsEnum.USER.getName())
                        .build())
                .money(BigDecimal.ZERO)
                .build();
        session.save(newUser);

        var result1 = userRep.findById(4L);
        assertThat(result1).isPresent();

        userRep.delete(4L);

        var result2 = userRep.findById(4L);
        assertThat(result2).isEmpty();

        session.getTransaction().commit();
    }

    @Test
    public void update() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.findById(1L);
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Nazar");

        user.setUsername("NazarAOAO");

        userRep.update(user);

        var result1 = userRep.findById(1L);
        assertThat(result).isPresent();
        var user1 = result.get();
        assertThat(user.getUsername()).isEqualTo("NazarAOAO");

        session.getTransaction().commit();
    }

    @Test
    public void findById() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        var result = userRep.findById(2L);
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Helena");

        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var userRep = new UserRepository(session);

        List<User> results = userRep.findAll();
        assertThat(results).hasSize(3);

        List<String> names = results.stream().map(User::getUsername).collect(toList());
        assertThat(names).containsExactlyInAnyOrder("Nazar", "Sergey", "Helena");

        session.getTransaction().commit();
    }*/


}