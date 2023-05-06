package org.example.mapper.statusMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusDtoMapper implements Mapper<StatusDto, Status> {
    private static final StatusDtoMapper INSTANCE = new StatusDtoMapper();
    public static StatusDtoMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public Status mapFrom(StatusDto object) {
        return Status.find(object.name()).get();
    }
}
