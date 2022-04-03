package com.managementsystem.profileservice.exception;

public class BadRequestParamException extends RuntimeException {

    public BadRequestParamException(String message) {
        super(message);
    }
}
