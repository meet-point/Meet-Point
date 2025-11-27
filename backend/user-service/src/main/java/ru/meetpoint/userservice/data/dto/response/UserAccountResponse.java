package ru.meetpoint.userservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.Role;
import ru.meetpoint.security.starter.data.enums.State;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
@Schema(
        description = "Complete user account information with security and status details",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"firstName\": \"Ivan\", " +
                "\"lastName\": \"Ivanov\", " +
                "\"email\": \"ivan.ivanov@example.com\", " +
                "\"state\": \"ACTIVE\", " +
                "\"roles\": [\"USER\", \"MODERATOR\"], " +
                "\"dateOfCreation\": \"2023-01-15T10:30:00\"}"
)
public record UserAccountResponse(
        @Schema(
                description = "Unique identifier of the user account",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(
                description = "User's first name",
                example = "Ivan",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @Schema(
                description = "User's middle name",
                example = "Ivanovich"
        )
        String middleName,

        @Schema(
                description = "User's last name",
                example = "Ivanov",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Schema(
                description = "User's email address",
                example = "ivan.ivanov@example.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "URL to user's profile avatar image",
                example = "https://example.com/avatars/user-123.jpg",
                format = "uri"
        )
        String avatarUrl,

        @Schema(
                description = "Current state/status of the user account",
                example = "ACTIVE",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        State state,

        @Schema(
                description = "Set of roles assigned to the user",
                example = "[\"USER\", \"MODERATOR\"]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Set<Role> roles,

        @Schema(
                description = "Timestamp when the account was created",
                example = "2023-01-15T10:30:00",
                format = "date-time",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Timestamp dateOfCreation,

        @Schema(
                description = "Timestamp of the last account update",
                example = "2023-11-20T14:45:30",
                format = "date-time"
        )
        Timestamp dateOfLastUpdate
) {
}
