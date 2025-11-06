package ru.meetpoint.authservice.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Standard response for successfully completed operations.")
public record OperationResponse(
        boolean isSuccess
) {
}
