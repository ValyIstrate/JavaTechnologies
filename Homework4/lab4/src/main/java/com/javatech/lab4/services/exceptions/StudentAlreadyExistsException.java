package com.javatech.lab4.services.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String message) {
        super("Student Already Exists Exception: " + message);
    }
}
