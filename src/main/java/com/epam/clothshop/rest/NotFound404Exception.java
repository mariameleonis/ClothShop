package com.epam.clothshop.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound404Exception extends RuntimeException {

    public NotFound404Exception() {
    }

    public NotFound404Exception(String message) {
        super(message);
    }

    public NotFound404Exception(Throwable cause) {
        super(cause);
    }

    public NotFound404Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFound404Exception(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
