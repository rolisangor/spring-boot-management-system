package com.management.system.authservice.controller.advice;

import com.management.system.authservice.exception.UserRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("METHOD_NOT_VALID_EXCEPTION_HANDLE_MESSAGE: {}", Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getFieldError().getDefaultMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(
            ConstraintViolationException exception) {
        log.error("VALIDATION_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        final List<String> validationErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ":" + violation.getMessage())
                .collect(Collectors.toList());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(StringUtils.join(validationErrors, ", "))
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler({UserRegistrationException.class})
    public ResponseEntity<?> handleUserRegistrationException(UserRegistrationException exception) {
        log.error("USER_REGISTRATION_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        ResponseError errorBody = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
