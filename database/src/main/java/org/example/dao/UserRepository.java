package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import org.example.entity.User;
import org.example.exception.DaoException;
import org.hibernate.Session;

import java.util.Optional;

import static org.example.entity.QUser.user;

public class UserRepository extends BaseRepository<Long, User> {
    private static final UserRepository INSTANCE = new UserRepository(User.class);

    public UserRepository(Class<User> clazz) {
        super(clazz);
    }

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public Optional<User> findByUsernameAndPassword(String username, String password, Session session) {
        try {
            return Optional.ofNullable(
                    new JPAQuery<User>(session)
                    .select(user)
                    .from(user)
                    .where(user.username.eq(username), user.password.eq(password))
                    .fetchOne()
            );
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueUsername(String username, Session session) {
        try {
            var result = Optional.ofNullable(
                    new JPAQuery<User>(session)
                            .select(user)
                            .from(user)
                            .where(user.username.eq(username))
                            .fetchOne()
            );
            return result.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniqueEmail(String email, Session session) {
        try {
            var result = Optional.ofNullable(
                    new JPAQuery<User>(session)
                            .select(user)
                            .from(user)
                            .where(user.email.eq(email))
                            .fetchOne()
            );
            return result.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public boolean isUniquePhoneNumber(String phoneNumber, Session session) {
        try {
            var result = Optional.ofNullable(
                    new JPAQuery<User>(session)
                            .select(user)
                            .from(user)
                            .where(user.phoneNumber.eq(phoneNumber))
                            .fetchOne()
            );
            return result.isEmpty();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
