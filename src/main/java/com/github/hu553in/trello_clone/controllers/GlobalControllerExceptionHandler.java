package com.github.hu553in.trello_clone.controllers;

import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getAllErrors()
                .forEach((error) -> errors.put(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ));
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException e) {
        return new HashMap<>() {{
            put(e.getName(), "is of invalid data type");
        }};
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    public Map<String, String> handleCustomMethodArgumentNotValid(final CustomMethodArgumentNotValidException e) {
        return new HashMap<>() {{
            put(e.getField(), e.getMessage());
        }};
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound() {
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handle() {
    }
}
