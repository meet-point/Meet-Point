package ru.meetpoint.authservice.data.dto.inner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "JwtTokenResponse", description = "Response containing JWT access and refresh tokens")
public record JwtTokenResponse(

        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdW...")
        String accessToken,

        @Schema(description = "JWT refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdW...")
        String refreshToken
) {
}
