package ru.meetpoint.userservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(
        description = "Brief information about the user friend",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"firstName\": \"Ivan\", " +
                "\"lastName\": \"Ivanov\", " +
                "\"avatarUrl\": \"https://example.com/avatars/user-123.jpg\"}"
)
public record FriendShortResponse(
        @Schema(
                description = "Unique identifier of the user",
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
                description = "URL to user's profile avatar image",
                example = "https://example.com/avatars/user-123.jpg",
                format = "uri",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String avatarUrl
) {
}
