package ru.meetpoint.authservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "EmailAvailabilityResponse", description = "Response for checking email availability")
public record EmailAvailabilityResponse(

        @Schema(description = "Boolean flag - is email available or not", example = "true")
        boolean isAvailable
) {
}
