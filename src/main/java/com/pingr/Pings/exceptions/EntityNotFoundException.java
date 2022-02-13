package com.pingr.Pings.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " #" + id + " not found");
    }

}
