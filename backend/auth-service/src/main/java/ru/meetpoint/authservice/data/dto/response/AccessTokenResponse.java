package ru.meetpoint.authservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AccessTokenResponse", description = "Response containing JWT access token")
public record AccessTokenResponse(

        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdW...")
        String accessToken
) {
}
