package org.example.exception;

import lombok.Getter;
import org.example.validator.Error;

import java.util.List;

public class ServiceException extends RuntimeException {
    @Getter
    private final List<Error> errors;
    public ServiceException(List<Error> errors) {
        this.errors = errors;
    }
}
