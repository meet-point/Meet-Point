package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(
        description = "Request to create a new user account",
        example = "{\"userId\": \"f5ed8a67-53eb-461d-998d-93a60433df9c\", " +
                "\"firstName\": \"Ivan\", " +
                "\"lastName\": \"Ivanov\", " +
                "\"dateOfBirth\": \"2000-06-12\"" +
                "\"email\": \"ivan.ivanov@example.com\"}"
)
public record CreateUserAccountRequest(
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
                description = "User's date of birth in ISO format",
                example = "2000-06-12",
                format = "date",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String dateOfBirth,

        @Schema(
                description = "User's email address",
                example = "ivan.ivanov@example.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "User's phone number in international format",
                example = "+7 (987) 654-32-10"
        )
        String phoneNumber,

        @Schema(
                description = "User's city of residence",
                example = "Kazan"
        )
        String city
) {
}
