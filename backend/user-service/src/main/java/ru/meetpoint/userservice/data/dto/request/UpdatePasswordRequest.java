package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(
        description = "Request to update user password",
        example = "{\"password\": \"NewSecurePassword123!\", " +
                "\"confirmPassword\": \"NewSecurePassword123!\"}"
)
public record UpdatePasswordRequest(
        @Schema(
                description = "New password (must meet security requirements)",
                example = "NewSecurePassword123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password,

        @Schema(
                description = "Password confirmation (must match password field)",
                example = "NewSecurePassword123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String confirmPassword
) {
}

