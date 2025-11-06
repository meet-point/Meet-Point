package ru.meetpoint.authservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(name = "RegistrationResponse", description = "Response for operation registration")
public record RegistrationResponse(
        @Schema(description = "Boolean flag - registration ended without errors", example = "false")
        boolean isSuccess,

        @Schema(description = "Id new registered user in UUID format", example = "52899421-4f62-447b-b84a-83b8afb64dbc")
        UUID userId
) {
}
