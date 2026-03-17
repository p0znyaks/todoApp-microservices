package com.p0znyaks.todo_app.exception;

import com.p0znyaks.todo_app.dto.ErrorResponse;
import com.p0znyaks.todo_app.dto.ErrorType;
import com.p0znyaks.todo_app.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.TASK_NOT_FOUND, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // TODO: централизованные константы для ошибок enum
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.USER_NOT_FOUND, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INVALID_PARAMETER, request.getRequestURI(), ex);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(ErrorType.VALIDATION_FAILED, request.getRequestURI(), ex);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}