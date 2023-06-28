package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;
import org.example.dto.positionDto.PositionDto;

import java.math.BigDecimal;

@Value
@Builder
public class UserDtoRequest {
    Long id;
    String username;
    PositionDto position;
    String email;
    String phoneNumber;
    BigDecimal money;
}
