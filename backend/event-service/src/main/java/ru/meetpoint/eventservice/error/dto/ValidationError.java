package ru.meetpoint.eventservice.error.dto;

import lombok.Builder;

@Builder
public record ValidationError(
        String field,
        String message
) {
}
