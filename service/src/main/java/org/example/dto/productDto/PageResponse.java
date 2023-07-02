package org.example.dto.productDto;

import lombok.Value;
import org.springframework.data.domain.Slice;

import java.util.List;

@Value
public class PageResponse {
    List<ProductDtoRequest> content;
    Metadata metadata;

    public static PageResponse of(Slice<ProductDtoRequest> slice) {
        var metadata = new Metadata(slice.getNumber(), slice.getSize(), slice.hasNext());
        return new PageResponse(slice.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        boolean isNextPagePresent;
    }
}
