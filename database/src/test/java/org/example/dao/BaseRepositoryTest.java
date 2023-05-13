package org.example.dao;

import lombok.Cleanup;
import org.example.entity.Position;
import org.example.entity.PositionsEnum;
import org.example.entity.User;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseRepositoryTest {
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
    public void save() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result1 = userRep.findById(4L, session);
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

        var result2 = userRep.findById(4L, session);
        assertThat(result2).isPresent();

        session.getTransaction().commit();
    }

    @Test
    public void delete() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result1 = userRep.findById(1L, session);
        assertThat(result1).isPresent();

        userRep.delete(1L, session);

        var result2 = userRep.findById(1L, session);
        assertThat(result2).isEmpty();

        session.getTransaction().commit();
    }

    @Test
    public void update() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.findById(1L, session);
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Nazar");

        session.getTransaction().commit();

        user.setUsername("NazarAOAO");

        session.beginTransaction();

        userRep.update(user, session);

        var result1 = userRep.findById(1L, session);
        assertThat(result).isPresent();
        var user1 = result.get();
        assertThat(user.getUsername()).isEqualTo("NazarAOAO");

        session.getTransaction().commit();
    }

    @Test
    public void findById() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var result = userRep.findById(1L, session);
        assertThat(result).isPresent();
        var user = result.get();
        assertThat(user.getUsername()).isEqualTo("Nazar");

        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userRep.findAll(session);
        assertThat(results).hasSize(3);

        List<String> names = results.stream().map(User::getUsername).collect(toList());
        assertThat(names).containsExactlyInAnyOrder("Nazar", "Sergey", "Helena");


        session.getTransaction().commit();
    }
}