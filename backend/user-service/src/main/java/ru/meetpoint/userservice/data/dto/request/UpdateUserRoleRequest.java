package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.Role;

import java.util.UUID;

@Builder
@Schema(
        description = "Request to assign or revoke a role for a user",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"role\": \"MODERATOR\", \"isAssigned\": true}"
)
public record UpdateUserRoleRequest(
        @Schema(
                description = "Unique identifier of the user whose role is being updated",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(
                description = "The role to be assigned or revoked",
                example = "MODERATOR",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Role role,

        @Schema(
                description = "Flag indicating whether to assign (true) or revoke (false) the role",
                example = "true",
                defaultValue = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        boolean isAssigned
) {
}