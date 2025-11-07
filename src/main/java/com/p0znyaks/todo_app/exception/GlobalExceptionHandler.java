package com.p0znyaks.todo_app.exception;

import lombok.RequiredArgsConstructor;
import com.p0znyaks.todo_app.dto.ErrorResponse;
import com.p0znyaks.todo_app.dto.ErrorType;
import com.p0znyaks.todo_app.dto.ValidationErrorResponse;
import com.p0znyaks.todo_app.util.ErrorResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(WebRequest request) {
        ErrorResponse errorResponse = errorResponseFactory.create(
                ErrorType.TASK_NOT_FOUND,
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // TODO: централизованные константы для ошибок enum
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(WebRequest request) {
        ErrorResponse errorResponse = errorResponseFactory.create(
                ErrorType.TASK_NOT_FOUND,
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                            WebRequest request) {
        ErrorResponse errorResponse = errorResponseFactory.create(
                ErrorType.INVALID_PARAMETER,
                request.getDescription(false),
                ex.getName()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                                    WebRequest request) {
        ValidationErrorResponse errorResponse = errorResponseFactory.createValidation(
                ex,
                request.getDescription(false)
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }
}