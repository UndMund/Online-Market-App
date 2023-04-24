package org.example.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private List<Position> positions;
    private String email;
    private String phoneNumber;
    private String password;
    private BigDecimal money;
}
