package org.example.dao;

import org.example.entity.BaseEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {
        E save(E entity, Session session);
        void delete(K id, Session session);

        void update(E entity, Session session);

        Optional<E> findById(K id, Session session);

        List<E> findAll(Session session);


}
