package org.example.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.Position;
import org.example.exception.DaoException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PositionDao implements Dao<Integer, Position> {
    private static final PositionDao INSTANCE = new PositionDao();

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    @Getter
    private List<Position> positions = new ArrayList<>();

    @Override
    public void update(Position entity, Session session) {
        try {
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Position> findById(Integer id, Session session) {
        try {
            return Optional.of(session.get(Position.class, id));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Position> findAll(Session session) {
        try {
            return session.createQuery("select p from Position p", Position.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(Integer id, Session session) {
        try {
            session.delete(
                    session.get(Position.class, id)
            );
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer save(Position entity, Session session) {
        try {
            entity.setId((Integer) session.save(entity));
            return entity.getId();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public Optional<Position> getPosition(String position, Session session) {
        if (positions.size() == 0) {
            this.positions.addAll(findAll(session));
        }
        return this.positions
                .stream()
                .filter(p -> p.getPositionName().equals(position))
                .findFirst();
    }
}
