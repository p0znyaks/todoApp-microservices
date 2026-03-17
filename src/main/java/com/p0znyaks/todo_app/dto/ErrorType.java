package com.p0znyaks.todo_app.dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum ErrorType {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter {0}"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "Validation failed");

    private final HttpStatus httpStatus;
    private final String messageTemplate;

    public int getStatusCode() {
        return httpStatus.value();
    }

    public String getMessage(Exception ...args) {
        return MessageFormat.format(messageTemplate, Arrays.toString(args));
    }

    public Map<String, String> getValidationMessage(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> errors
                .put(err.getField(), err.getDefaultMessage()));

        return errors;
    }
}