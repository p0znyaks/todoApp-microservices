package com.p0znyaks.todo_app.util;

import com.p0znyaks.todo_app.dto.ErrorResponse;
import com.p0znyaks.todo_app.dto.ErrorType;
import com.p0znyaks.todo_app.dto.ValidationErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ErrorResponseFactory {

    public ErrorResponse create(ErrorType errorType, String path, Object ...messageArgs) {
        return new ErrorResponse(
                errorType.name(),
                errorType.getStatusCode(),
                errorType.formatMessage(messageArgs),
                LocalDateTime.now(),
                path
        );
    }

    public ValidationErrorResponse createValidation(ErrorType errorType, String path,
                                                    Map<String, String> fieldErrors) {
        return new ValidationErrorResponse(
                errorType.name(),
                errorType.getStatusCode(),
                fieldErrors,
                LocalDateTime.now(),
                path
        );
    }

    public ValidationErrorResponse createValidation(MethodArgumentNotValidException ex, String path) {
        Map<String, String> fieldErrors =
                ErrorType.VALIDATION_FAILED.formatValidationMessage(ex);

        return createValidation(ErrorType.VALIDATION_FAILED, path, fieldErrors);
    }
}