package com.p0znyaks.todo_app.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String title;
    private final int statusCode;
    private final String detail;
    private final LocalDateTime timestamp;
    private final String path;

    public ErrorResponse(ErrorType errorType, String path, MethodArgumentTypeMismatchException ...ex) {
        this.title = errorType.name();
        this.statusCode = errorType.getStatusCode();
        this.detail = errorType.getMessage(ex);
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}