package com.management.system.authservice.exception;

public class PasswordValidationException extends RuntimeException{

    public PasswordValidationException(String message) {
        super(message);
    }
}
