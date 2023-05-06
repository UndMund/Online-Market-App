package org.example.dao;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface Dao <K, E> {
        void update(E entity, Session session);

        Optional<E> findById(K id, Session session);

        List<E> findAll(Session session);

        void deleteById(K id, Session session);

        K save(E entity, Session session);
}
