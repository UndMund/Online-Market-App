package org.example.dto.orderDto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class OrderDtoRequest {
    String username;
    String productName;
    String sellerName;
    BigDecimal price;
    LocalDate orderDate;
}
