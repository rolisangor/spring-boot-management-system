package com.management.system.authservice.controller.advice;

import com.management.system.authservice.exception.InternalServiceException;
import com.management.system.authservice.exception.RoleNotFoundException;
import com.management.system.authservice.exception.UserNotFoundException;
import com.management.system.authservice.exception.UserRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("METHOD_NOT_VALID_EXCEPTION_HANDLE_MESSAGE: {}",
                Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        return getErrorBody(exception.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleConstraintViolation(ConstraintViolationException exception) {
        log.error("VALIDATION_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        final List<String> validationErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ":" + violation.getMessage())
                .collect(Collectors.toList());
        return getErrorBody(StringUtils.join(validationErrors, ", "), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleAllExceptions(Exception exception) {
        log.error("ALL_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UserRegistrationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleUserRegistrationException(UserRegistrationException exception) {
        log.error("USER_REGISTRATION_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUserNotFoundException(UserNotFoundException exception) {
        log.error("USER_NOT_FOUND_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleRoleNotFoundException(RoleNotFoundException exception) {
        log.error("ROLE_NOT_FOUND_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleInternalServiceException(InternalServiceException exception) {
        log.error("INTERNAL_SERVICE_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseError getErrorBody(String message, HttpStatus status) {
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(status.value()).build();
    }
}
