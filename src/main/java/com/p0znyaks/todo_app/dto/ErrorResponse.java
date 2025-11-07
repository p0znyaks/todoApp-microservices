package com.p0znyaks.todo_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String title;
    private int statusCode;
    private String detail;
    private LocalDateTime timestamp;
    private String path;
}