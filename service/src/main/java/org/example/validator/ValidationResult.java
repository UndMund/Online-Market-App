package org.example.validator;

import lombok.Getter;

import jakarta.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationResult <T> {
    @Getter
    private final List<Error> errors = new ArrayList<>();
    public void setValidationErrors(Set<ConstraintViolation<T>> validates) {
        errors.addAll(validates.stream()
                .map(v -> Error.of(v.getMessage()))
                .toList());
    }
    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
