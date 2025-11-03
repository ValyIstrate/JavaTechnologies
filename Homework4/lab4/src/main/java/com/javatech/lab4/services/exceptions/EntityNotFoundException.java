package com.javatech.lab4.services.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super("Entity Not Found Exception:" + message);
    }
}
