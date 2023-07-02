package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.positionDto.PositionDto;
import org.example.exception.ServiceException;
import org.example.mapper.PositionMapper;
import org.example.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public List<PositionDto> findAll() {
        try {
            return positionRepository.findAll()
                    .stream()
                    .map(positionMapper::toPositionDto)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
