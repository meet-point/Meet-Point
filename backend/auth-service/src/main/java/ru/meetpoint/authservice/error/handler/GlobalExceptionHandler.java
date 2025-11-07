package ru.meetpoint.authservice.error.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.error.dto.ValidationErrorResponse;
import ru.meetpoint.authservice.error.exception.*;
import ru.meetpoint.security.starter.data.enums.ErrorCode;
import ru.meetpoint.security.starter.response.ApiErrorResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMessageProperties errorMessageProperties;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<ValidationErrorResponse.ValidationError> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ValidationErrorResponse.ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

        log.info("Validation failed: fields={}, errorCount={}",
                validationErrors.stream().map(ValidationErrorResponse.ValidationError::field).collect(Collectors.toSet()),
                validationErrors.size());

        return ValidationErrorResponse.builder()
                .code(400)
                .exceptionMessage(errorMessageProperties.getInvalidInputs())
                .errorCode(ErrorCode.INVALID_INPUT_DATA)
                .validationErrors(validationErrors)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleBadRequestException(BadRequestException badRequestException) {
        log.warn("Bad request exception: code={}, message={}", badRequestException.getErrorCode(),
                badRequestException.getMessage());
        return ValidationErrorResponse.builder()
                .code(400)
                .exceptionMessage(badRequestException.getMessage())
                .errorCode(badRequestException.getErrorCode())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUnauthorizedException(UnauthorizedException unauthorizedException) {
        log.warn("Unauthorized exception: code={}, message={}", unauthorizedException.getErrorCode(),
                unauthorizedException.getMessage());
        return ApiErrorResponse.builder()
                .code(401)
                .exceptionMessage(unauthorizedException.getMessage())
                .errorCode(unauthorizedException.getErrorCode())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleForbiddenException(ForbiddenException forbiddenException) {
        log.warn("Access denied exception: code={}, message={}", forbiddenException.getErrorCode(),
                forbiddenException.getMessage());
        return ApiErrorResponse.builder()
                .code(403)
                .exceptionMessage(forbiddenException.getMessage())
                .errorCode(forbiddenException.getErrorCode())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        log.warn("Access denied: {}", accessDeniedException.getMessage());
        return ApiErrorResponse.builder()
                .code(403)
                .exceptionMessage(errorMessageProperties.getForbidden())
                .errorCode(ErrorCode.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        log.warn("Resource not found exception: code={}, message={}", notFoundException.getErrorCode(),
                notFoundException.getMessage());
        return ApiErrorResponse.builder()
                .code(404)
                .exceptionMessage(notFoundException.getMessage())
                .errorCode(notFoundException.getErrorCode())
                .build();
    }

    @ExceptionHandler(InternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleInternalException(InternalException internalException) {
        log.error("Internal error: code={}, message={}", internalException.getErrorCode(),
                internalException.getMessage());
        return ApiErrorResponse.builder()
                .code(500)
                .exceptionMessage(errorMessageProperties.getInternalError())
                .errorCode(internalException.getErrorCode())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneralException(Exception exception) {
        log.error("Unhandled error: type={}, message={}", exception.getClass().getSimpleName(),
                exception.getMessage(), exception);
        return ApiErrorResponse.builder()
                .code(500)
                .exceptionMessage(errorMessageProperties.getInternalError())
                .errorCode(ErrorCode.INTERNAL)
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceException(ServiceException serviceException) {
        log.warn("Service exception: status={}, code={}, message={}", serviceException.getHttpStatus().value(),
                serviceException.getErrorCode(), serviceException.getMessage());
        return ResponseEntity.status(serviceException.getHttpStatus())
                .body(ApiErrorResponse.builder()
                        .code(serviceException.getHttpStatus().value())
                        .exceptionMessage(serviceException.getMessage())
                        .errorCode(serviceException.getErrorCode())
                        .build()
                );
    }
}
