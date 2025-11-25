package ru.meetpoint.eventservice.error.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int code,
        String exception,
        String message
) {
}
