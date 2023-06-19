package org.example.mapper;

import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {
    public Status toStatus(StatusDto object) {
        return Status.find(object.name()).get();
    }

    public StatusDto toStatusDto(Status object) {
        return StatusDto.find(object.name()).get();
    }
}
