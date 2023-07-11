package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.positionDto.PositionDto;
import org.example.entity.Position;
import org.example.repository.PositionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionMapper {
    private final PositionRepository positionRepository;

    public Position toPosition(String position) {
        return positionRepository.findByPositionName(position).orElseThrow();
    }

    public PositionDto toPositionDto(Position position) {
        return new PositionDto(position.getPositionName());
    }
}
