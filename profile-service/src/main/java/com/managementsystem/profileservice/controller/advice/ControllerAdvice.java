package com.managementsystem.profileservice.controller.advice;

import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.exception.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
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
public class ControllerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleDefaultException(Exception exception) {
        log.error("DEFAULT_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

    @ExceptionHandler({ProfileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleProfileNotFoundException(ProfileNotFoundException exception) {
        log.error("PROFILE_NOT_FOUND_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestParamException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleBadRequestParamException(BadRequestParamException exception) {
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
