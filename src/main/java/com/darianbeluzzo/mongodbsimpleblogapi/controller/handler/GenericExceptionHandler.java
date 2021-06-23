package com.darianbeluzzo.mongodbsimpleblogapi.controller.handler;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GenericExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError onException(final Exception exception) {
        logger.error("error: general, message: {}", exception.getMessage());
        var message = Optional.ofNullable(exception.getMessage()).orElse("");
        return ApiError.builder().code("INTERNAL_SERVER_ERROR").message(message).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError onMethodArgumentNotValidException(final MethodArgumentNotValidException exception, final Errors errors) {
        logger.error("error: validation, message: {}", exception.getMessage());

        var details = errors.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, v -> Optional.ofNullable(v.getDefaultMessage()).orElse("")));

        return ApiError.builder().code("REQUEST_VALIDATION_ERROR").message("Some fields are invalid").details(details)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onResourceNotFoundException(final ResourceNotFoundException exception) {
        logger.error("error: validation, message: {}", exception.getMessage());
        return ApiError.builder().code("RESOURCE_NOT_FOUND").message(exception.getMessage()).build();
    }
}
