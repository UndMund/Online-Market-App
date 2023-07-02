package org.example.dto.filter;

public record ProductFilter(String categoryFilter,
                            String priceFilter) {
    public boolean isEmpty() {
        return this.categoryFilter == null & this.priceFilter == null;
    }
}
