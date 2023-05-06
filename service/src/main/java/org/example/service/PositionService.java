package org.example.service;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import org.example.dao.PositionDao;
import org.example.entity.Position;
import org.example.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionService {
    private static final PositionService INSTANCE = new PositionService();

    private static final PositionDao positionDao = PositionDao.getInstance();

    public static PositionService getInstance() {
        return INSTANCE;
    }

    public List<String> getPositions() {
        return positionDao.getPositions()
                .stream()
                .map(Position::getPositionName)
                .collect(Collectors.toList());
    }

    public Optional<Position> getPosition(String position) {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        return positionDao.getPosition(position, session);
    }
}
