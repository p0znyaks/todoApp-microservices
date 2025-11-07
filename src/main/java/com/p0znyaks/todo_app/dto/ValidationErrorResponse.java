package com.p0znyaks.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String title;
    private int statusCode;
    private Map<String, String> detail;
    private LocalDateTime timestamp;
    private String path;
}
