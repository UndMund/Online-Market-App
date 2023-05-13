package org.example.dto.categoryDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDtoResponse {
    String categoryName;
}