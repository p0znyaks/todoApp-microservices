package com.p0znyaks.todo_app.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final String title;
    private final int statusCode;
    private final Map<String, String> detail;
    private final LocalDateTime timestamp;
    private final String path;

    public ValidationErrorResponse(ErrorType errorType, String path, MethodArgumentNotValidException ex) {
        this.title = errorType.name();
        this.statusCode = errorType.getStatusCode();
        this.detail = errorType.getValidationMessage(ex);
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}