package ru.meetpoint.userservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.sql.Date;

@Builder
@Schema(
        description = "Request to update user profile information",
        example = "{\"firstName\": \"Ivan\", " +
                "\"lastName\": \"Ivanov\", " +
                "\"email\": \"ivan.ivanov@example.com\", " +
                "\"city\": \"Kazan\", " +
                "\"aboutMe\": \"Passionate about creativity\"}"
)
public record UpdateProfileInfoRequest(
        @Schema(
                description = "User's first name",
                example = "Ivan"
        )
        String firstName,

        @Schema(
                description = "User's middle name (patronymic)",
                example = "Ivanovich"
        )
        String middleName,

        @Schema(
                description = "User's last name",
                example = "Ivanov"
        )
        String lastName,

        @Schema(
                description = "User's date of birth in ISO format",
                example = "2000-06-12",
                format = "date"
        )
        Date dateOfBirth,

        @Schema(
                description = "User's phone number in international format",
                example = "+7 (987) 654-32-10"
        )
        String phoneNumber,

        @Schema(
                description = "User's city of residence",
                example = "Kazan"
        )
        String city,

        @Schema(
                description = "User's biography or personal description",
                example = "Passionate about creativity and helping others maintain emotional stability"
        )
        String aboutMe
) {
}
