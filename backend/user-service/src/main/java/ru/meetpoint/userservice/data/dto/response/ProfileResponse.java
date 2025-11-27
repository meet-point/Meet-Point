package ru.meetpoint.userservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
@Schema(
        description = "Complete user profile information including personal details and metadata",
        example = "{\"firstName\": \"John\", " +
                "\"lastName\": \"Ivanov\", " +
                "\"dateOfBirth\": \"2000-06-12\", " +
                "\"email\": \"ivan.ivanov@example.com\", " +
                "\"phoneNumber\": \"+7 (987) 654-32-10\", " +
                "\"city\": \"Kazan\", " +
                "\"avatarUrl\": \"https://example.com/avatars/user-123.jpg\", " +
                "\"aboutMe\": \"Passionate about creativity\", " +
                "\"dateOfCreation\": \"2023-01-15T10:30:00\"}"
)
public record ProfileResponse(
        @Schema(
                description = "User's first name",
                example = "Ivan",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @Schema(
                description = "User's middle name",
                example = "Ivanovich",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String middleName,

        @Schema(
                description = "User's last name",
                example = "Ivanov",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Schema(
                description = "User's date of birth",
                example = "2000-06-12",
                format = "date",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Date dateOfBirth,

        @Schema(
                description = "User's email address",
                example = "ivan.ivanov@example.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "User's phone number in international format",
                example = "+7 (987) 654-32-10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String phoneNumber,

        @Schema(
                description = "User's city of residence",
                example = "Kazan",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String city,

        @Schema(
                description = "URL to user's profile avatar image",
                example = "https://example.com/avatars/user-123.jpg",
                format = "uri",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String avatarUrl,

        @Schema(
                description = "User's biography or personal description",
                example = "Passionate about creativity and helping others maintain emotional stability",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String aboutMe,

        @Schema(
                description = "Timestamp when the profile was created",
                example = "2023-01-15T10:30:00",
                format = "date-time",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Timestamp dateOfCreation
) {
}
