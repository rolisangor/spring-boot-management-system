package com.managementsystem.profileservice.controller.advice;

import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.exception.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllExceptions(Exception exception) {
        log.error("ALL_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ProfileNotFoundException.class})
    public ResponseEntity<?> handleProfileNotFoundException(ProfileNotFoundException exception) {
        log.error("PROFILE_NOT_FOUND_HANDLE_MESSAGE: {}", exception.getMessage());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestParamException.class})
    public ResponseEntity<?> handleBadRequestParamException(BadRequestParamException exception) {
        log.error("BAD_REQUEST_PARAM_HANDLE_MESSAGE: {}", exception.getMessage());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
