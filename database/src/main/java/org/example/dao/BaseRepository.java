package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.BaseEntity;
import org.example.exception.DaoException;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseRepository <K extends Serializable, E extends BaseEntity<K>>
        implements Repository <K, E> {
    protected final EntityManager entityManager;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {
        try {
            log.info("try to save " + entity);
            entityManager.persist(entity);
            log.info("successfully saved " + entity);
            return entity;
        } catch (Exception e) {
            log.error("error save " + entity , e);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(K id) {
        try {
            log.info("try to delete from " + clazz + " id: " + id);
            entityManager.remove(entityManager.find(clazz, id));
            entityManager.flush();
            log.info("successfully deleted from " + clazz + " id: " + id);
        } catch (Exception e) {
            log.error("error delete entity, id: " + id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(E entity) {
        try {
            log.info("try to update " + entity);
            entityManager.merge(entity);
            log.info("successfully updated " + entity);
        } catch (Exception e) {
            log.error("error update " + entity, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<E> findById(K id) {
        try {
            log.info("try to find in " + clazz + " id: " + id);
            Optional<E> optional = Optional.ofNullable(entityManager.find(clazz, id));
            log.info("successfully return entity from " + clazz + " with id: " + id);
            return optional;
        } catch (Exception e) {
            log.error("error find entity, id: " + id, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<E> findAll() {
        try {
            log.info("try to find all from " + clazz);
            var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
            criteria.from(clazz);
            var list = entityManager.createQuery(criteria).getResultList();
            log.info("successfully returned " + clazz + " list");
            return list;
        } catch (Exception e) {
            log.error("error find all " + clazz);
            throw new DaoException(e);
        }
    }
}
