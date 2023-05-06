package org.example.mapper.statusMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.statusDto.StatusDto;
import org.example.entity.Status;
import org.example.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusMapper implements Mapper<Status, StatusDto> {
    private static final StatusMapper INSTANCE = new StatusMapper();
    public static StatusMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public StatusDto mapFrom(Status object) {
        return StatusDto.find(object.name()).get();
    }
}
