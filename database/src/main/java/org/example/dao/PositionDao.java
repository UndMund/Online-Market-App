package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Position;
import org.example.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PositionDao {
    private static final PositionDao INSTANCE = new PositionDao();

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    private static String FIND_ALL_SQL = """
            SELECT id, position_name         
            FROM position
            """;

    private static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static String FIND_POSITION_BY_USER = """
            SELECT p.id, p.position_name
            FROM position p 
            JOIN user_position up 
            ON p.id = up.position_id
            WHERE up.user_id = ?
            """;


    public Optional<Position> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Position position = null;
            statement.setInt(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                position = buildPosition(result);
            }
            return Optional.ofNullable(position);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Position> findPositionsByUserId(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_POSITION_BY_USER)) {
            List<Position> positions = new ArrayList<>();
            statement.setLong(1, id);
            var result = statement.executeQuery();
            while (result.next()) {
                positions.add(buildPosition(result));
            }
            return positions;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Position buildPosition(ResultSet result) throws SQLException {
        return Position.find(result.getString("position_name")).get();
    }
}
