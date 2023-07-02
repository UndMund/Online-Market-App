package org.example.dto.filter;

import lombok.experimental.UtilityClass;
import org.example.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class FilterResolver {
    public static Pageable priceFilterHandler(int page, int size, ProductFilter productFilter) {
        if (PriceFilterEnum.Ascending.name().equals(productFilter.priceFilter())) {
            return PageRequest.of(page, size, Sort.by(Product.Fields.price).ascending());

        } else if (PriceFilterEnum.Descending.name().equals(productFilter.priceFilter())) {
            return PageRequest.of(page, size, Sort.by(Product.Fields.price).descending());

        } else {
            return PageRequest.of(page, size);
        }
    }
}
