package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Position;
import org.example.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }
}
