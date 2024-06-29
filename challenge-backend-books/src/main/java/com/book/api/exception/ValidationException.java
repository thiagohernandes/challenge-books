package com.book.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
