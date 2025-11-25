package ru.meetpoint.eventservice.error.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ValidationErrorResponse(
        int code,
        String exception,
        String message,
        List<ValidationError> validationErrors
) {
}
