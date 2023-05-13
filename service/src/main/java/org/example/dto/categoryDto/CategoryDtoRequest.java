package org.example.dto.categoryDto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CategoryDtoRequest {
    Integer id;
    String categoryName;
}
