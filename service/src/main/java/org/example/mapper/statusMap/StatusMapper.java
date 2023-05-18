package org.example.mapper.statusMap;

import lombok.RequiredArgsConstructor;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.mapper.Mapper;

@RequiredArgsConstructor
public class StatusMapper implements Mapper<Status, StatusDto> {
    @Override
    public StatusDto mapFrom(Status object) {
        return StatusDto.find(object.name()).get();
    }
}
