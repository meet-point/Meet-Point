package ru.meetpoint.userservice.data.dto.response;

import java.util.UUID;

public record OperationResponse(
        boolean isSuccess,
        UUID entityId
) {
}
