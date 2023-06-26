package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.positionDto.PositionDto;
import org.example.mapper.PositionMapper;
import org.example.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public List<PositionDto> findAll() {
        return positionRepository.findAll()
                .stream()
                .map(positionMapper::toPositionDto)
                .toList();
    }
}
