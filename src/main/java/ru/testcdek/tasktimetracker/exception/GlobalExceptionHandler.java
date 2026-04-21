package ru.testcdek.tasktimetracker.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException exception) {
        return buildError(
                HttpStatus.NOT_FOUND,
                "not found",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidTimeRecordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTimeRecord(InvalidTimeRecordException exception) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "bad request",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return buildError(
                HttpStatus.BAD_REQUEST,
                "validation failed",
                "validation error",
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException exception) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "bad request",
                "invalid request body or enum value"
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "bad request",
                "invalid request parameter type"
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParam(MissingServletRequestParameterException exception) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "bad request",
                "missing required request parameter: " + exception.getParameterName()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "bad request",
                exception.getMessage()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException exception) {
        return buildError(
                HttpStatus.UNAUTHORIZED,
                "unauthorized",
                "invalid username or password"
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        return buildError(
                HttpStatus.FORBIDDEN,
                "forbidden",
                "access denied"
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException exception) {
        return buildError(
                HttpStatus.NOT_FOUND,
                "not found",
                "resource not found"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonException(Exception exception) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "internal server error",
                exception.getMessage()
        );
    }

    
    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String error, String message) {
        return buildError(status, error, message, null);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String error, String message, Object details) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                details
        );
        return ResponseEntity.status(status).body(response);
    }
}