package ru.meetpoint.eventservice.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.meetpoint.eventservice.error.exception.BadRequestException;
import ru.meetpoint.eventservice.error.exception.ForbiddenException;
import ru.meetpoint.eventservice.error.exception.NotFoundException;
import ru.meetpoint.eventservice.error.exception.ServiceException;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.security.starter.response.ValidationErrorResponse;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationErrorResponse.ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ValidationErrorResponse.ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

        ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .exceptionMessage(exception.getMessage())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(BadRequestException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .exceptionMessage(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(ForbiddenException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .exceptionMessage(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .exceptionMessage(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceException(ForbiddenException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exceptionMessage(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
