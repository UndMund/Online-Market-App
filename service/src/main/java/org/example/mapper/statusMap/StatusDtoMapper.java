package org.example.mapper.statusMap;

import lombok.RequiredArgsConstructor;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.mapper.Mapper;

@RequiredArgsConstructor
public class StatusDtoMapper implements Mapper<StatusDto, Status> {
    @Override
    public Status mapFrom(StatusDto object) {
        return Status.find(object.name()).get();
    }
}
