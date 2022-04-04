package com.managementsystem.profileservice.controller.advice;

import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.exception.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleProfileNotFoundException(Exception exception) {
        log.error("ALL_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ProfileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleProfileNotFoundException(ProfileNotFoundException exception) {
        log.error("PROFILE_NOT_FOUND_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestParamException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleInternalServiceException(BadRequestParamException exception) {
        log.error("BAD_REQUEST_PARAM_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseError getErrorBody(String message, HttpStatus status) {
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(status.value()).build();
    }
}
