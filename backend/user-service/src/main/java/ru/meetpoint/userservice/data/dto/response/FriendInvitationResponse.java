package ru.meetpoint.userservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(
        description = "Friend invitation details with user information and message",
        example = "{\"shortResponse\": " +
                    "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                    "\"firstName\": \"Ivan\", " +
                    "\"lastName\": \"Ivanov\"}, " +
                "\"shortMessage\": \"Let's be friends!\"}"
)
public record FriendInvitationResponse(
        @Schema(
                description = "Abbreviated information about the recipient of the invitation"
        )
        FriendShortResponse shortResponse,

        @Schema(
                description = "Personal message included with the friend invitation",
                example = "Hi! I'd like to connect with you on this platform. Let's be friends!"
        )
        String shortMessage
) {
}
