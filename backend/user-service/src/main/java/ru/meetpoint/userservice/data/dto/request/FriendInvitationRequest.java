package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(
        description = "Request to send a friend invitation",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"friendId\": \"5306b783-8f07-414e-aaac-01a2ab45c721\", " +
                "\"shortMessage\": \"Let's be friends!\"}"
)
public record FriendInvitationRequest(
        @Schema(
                description = "Unique identifier of the user sending the invitation",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(
                description = "Unique identifier of the user receiving the invitation",
                example = "5306b783-8f07-414e-aaac-01a2ab45c721",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID friendId,

        @Schema(
                description = "Short personal message to include with the invitation",
                example = "Let's be friends!"
        )
        String shortMessage
) {
}
