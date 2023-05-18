package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.exception.DaoException;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.example.entity.QUser.user;
@Slf4j
public class UserRepository extends BaseRepository<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try {
            log.info("try to find by name: " + username + " and password: " + password);
            var result = Optional.ofNullable(
                    new JPAQuery<User>(entityManager)
                    .select(user)
                    .from(user)
                    .where(user.username.eq(username), user.password.eq(password))
                    .fetchOne()
            );
            log.info("successfully return user: " + result);
            return result;
        } catch (Exception e) {
            log.error("error find user with username: " + username + " and password: " + password, e);
            throw new DaoException(e);
        }
    }

    public boolean isUniqueUsername(String username) {
        try {
            log.info("try to check name: " + username + " unique");
            var result = Optional.ofNullable(
                    new JPAQuery<User>(entityManager)
                            .select(user)
                            .from(user)
                            .where(user.username.eq(username))
                            .fetchOne()
            );
            log.info("successfully check name unique (is username unique?): " + result.isEmpty());
            return result.isEmpty();
        } catch (Exception e) {
            log.error("error check name unique: " + username, e);
            throw new DaoException(e);
        }
    }

    public boolean isUniqueEmail(String email) {
        try {
            log.info("try to check email: " + email + " unique");
            var result = Optional.ofNullable(
                    new JPAQuery<User>(entityManager)
                            .select(user)
                            .from(user)
                            .where(user.email.eq(email))
                            .fetchOne()
            );
            log.info("successfully check email unique (is email unique?): " + result.isEmpty());
            return result.isEmpty();
        } catch (Exception e) {
            log.error("error check email unique: " + email, e);
            throw new DaoException(e);
        }
    }

    public boolean isUniquePhoneNumber(String phoneNumber) {
        try {
            log.info("try to check phone number: " + phoneNumber + " unique");
            var result = Optional.ofNullable(
                    new JPAQuery<User>(entityManager)
                            .select(user)
                            .from(user)
                            .where(user.phoneNumber.eq(phoneNumber))
                            .fetchOne()
            );
            log.info("successfully check phone number unique (is phone number unique?): " + result.isEmpty());
            return result.isEmpty();
        } catch (Exception e) {
            log.error("error check phone number unique: " + phoneNumber, e);
            throw new DaoException(e);
        }
    }
}
