package ru.meetpoint.eventservice.data.dto.response.operation;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OperationResponse(
        boolean isSuccess,
        UUID entityId
) {
}
