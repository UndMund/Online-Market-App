package org.example.dto.userDto;

import lombok.Builder;
import lombok.Value;
import org.example.entity.Position;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class UserDtoRequest {
    Long id;
    String userName;
    List<Position> positions;
    String email;
    String phoneNumber;
    BigDecimal money;
}
