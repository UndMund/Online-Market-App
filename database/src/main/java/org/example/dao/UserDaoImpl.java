package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
//join fetch для many to one--
@NoArgsConstructor(access = PRIVATE)
public class UserDaoImpl implements Dao<Long, User> {
    private static final PositionDao positionDao = PositionDao.getInstance();
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public void update(User entity, Session session) {
        try {
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id, Session session) {
        try {
            return Optional.of(session.get(User.class, id));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByUsernameAndPassword(String username, String password, Session session) {
        try {
            var result = session.createQuery("""
                            select 
                            u
                            from User u
                            where username = :username
                            and password = :password
                            """)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .list();
            Optional<User> user = Optional.of((User) result.get(0));
            return user;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll(Session session) {
        try {
            return session.createQuery("select u from User u", User.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(Long id, Session session) {
        try {
            session.delete(
                    session.get(User.class, id)
            );
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long save(User entity, Session session) {
        try {
            entity.setMoney(BigDecimal.ZERO);
            var newId = (Long) session.save(entity);
            positionDao.findById(entity.getPosition().getId(), session)
                    .get()
                    .addUser(entity);
            return newId;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueUsername(String username, Session session) {
        try {
            var result = session.createQuery("""
                            select 
                            u
                            from User u
                            where username = :username                           
                            """)
                    .setParameter("username", username)
                    .list();
            Optional<User> user = result.stream().findFirst();
            return user.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueEmail(String email, Session session) {
        try {
            var result = session.createQuery("""
                            select 
                            u
                            from User u
                            where email = :email                           
                            """)
                    .setParameter("email", email)
                    .list();
            Optional<User> user = result.stream().findFirst();
            return user.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniquePhoneNumber(String phoneNumber, Session session) {
        try {
            var result = session.createQuery("""
                            select 
                            u
                            from User u
                            where phoneNumber = :phoneNumber                           
                            """)
                    .setParameter("phoneNumber", phoneNumber)
                    .list();
            Optional<User> user = result.stream().findFirst();
            return user.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
