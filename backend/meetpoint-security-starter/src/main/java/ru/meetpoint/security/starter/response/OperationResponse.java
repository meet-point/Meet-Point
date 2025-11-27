package ru.meetpoint.security.starter.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(
        description = "Standard response for successfully completed operations",
        example = "{\"isSuccess\": true, " +
                "\"entityId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\"}"
)
public record OperationResponse(
        @Schema(
                description = "Flag indicating whether the operation was successful",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        boolean isSuccess,

        @Schema(
                description = "Unique identifier of the entity affected by the operation (or created/updated entity)",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c"
        )
        UUID entityId
) {
}