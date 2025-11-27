package ru.meetpoint.userservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.userservice.data.entity.enums.ComplaintStatus;

import java.util.UUID;

@Builder
@Schema(
        description = "Summary information about a user complaint",
        example = "{\"complaintId\": \"db9a4b57-a017-44dd-9c24-97e84636bf3b\", " +
                "\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"userFullName\": \"Ivan Ivanovich Ivanov\", " +
                "\"authorId\": \"5306b783-8f07-414e-aaac-01a2ab45c721\", " +
                "\"authorFullName\": \"Vasiliy Vasilievich Vasiliev\", " +
                "\"status\": \"UNCHECKED\", " +
                "\"shortReason\": \"Inappropriate behavior\"" +
                "\"description\": \"The user was rude and used inappropriate language\"}"
)
public record UserComplaintResponse(
        @Schema(
                description = "Unique identifier of the complaint",
                example = "db9a4b57-a017-44dd-9c24-97e84636bf3b",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID complaintId,

        @Schema(
                description = "Unique identifier of the user being complained about",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(
                description = "Full name of the user being complained about",
                example = "Ivan Ivanovich Ivanov",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String userFullName,

        @Schema(
                description = "Unique identifier of the user who filed the complaint",
                example = "5306b783-8f07-414e-aaac-01a2ab45c721",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID authorId,

        @Schema(
                description = "Full name of the user who filed the complaint",
                example = "Vasiliy Vasilievich Vasiliev",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String authorFullName,

        @Schema(
                description = "Current status of the complaint",
                example = "UNCHECKED",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        ComplaintStatus status,

        @Schema(
                description = "Brief reason for the complaint",
                example = "Inappropriate behavior",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String shortReason,

        @Schema(
                description = "Detailed description of the complaint and its context",
                example = "The user was rude, used inappropriate language, and violated community guidelines during a conversation",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description
) {
}
