package com.pingr.Pings.exceptions;

public class EntityValidationException extends RuntimeException {
    public EntityValidationException(String invalidAttributeName) {
        super(invalidAttributeName + " is invalid");
    }

    public EntityValidationException(String invalidAttributeName, Throwable cause) {
        super(invalidAttributeName + " is invalid", cause);
    }
}