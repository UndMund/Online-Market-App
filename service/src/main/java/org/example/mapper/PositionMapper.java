package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entity.Position;
import org.example.repository.PositionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionMapper {
    private final PositionRepository positionRepository;

    Position toPosition(String position) {
        return positionRepository.findByPositionName(position).get();
    };

    String toPositionDto(Position position) {
        return position.getPositionName();
    };
}
