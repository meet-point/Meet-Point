package ru.meetpoint.authservice.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.authservice.error.enums.ErrorCode;

@Builder
@Schema(description = "Standard error response for API.")
public record ApiErrorResponse(

        @Schema(description = "HTTP status code", example = "404")
        int code,

        @Schema(description = "Error message", example = "User not found!")
        String exceptionMessage,

        @Schema(description = "Enum value with error status", example = "USER_DATA_NOT_FOUND")
        ErrorCode errorCode
) {
}
