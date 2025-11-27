package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(
        description = "Request to create a complaint against a user",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"authorId\": \"5306b783-8f07-414e-aaac-01a2ab45c721\", " +
                "\"shortReason\": \"Inappropriate behavior\", " +
                "\"description\": \"The user was rude and inappropriate\"}"
)
public record CreateUserComplaintRequest(
        @Schema(
                description = "Unique identifier of the user being complained about",
                example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID userId,

        @Schema(
                description = "Unique identifier of the user filing the complaint",
                example = "5306b783-8f07-414e-aaac-01a2ab45c721",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID authorId,

        @Schema(
                description = "Brief reason for the complaint",
                example = "Inappropriate behavior",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String shortReason,

        @Schema(
                description = "Detailed description of the complaint",
                example = "The user was rude, used inappropriate language, and violated community guidelines",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description
) {
}
