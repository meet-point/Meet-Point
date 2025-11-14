package ru.meetpoint.security.starter.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.ErrorCode;

@Builder
@Schema(description = "Standard error response for API.")
public record ApiErrorResponse(

        @Schema(description = "HTTP status code", example = "401")
        int code,

        @Schema(description = "Error message", example = "User unauthorized!")
        String exceptionMessage,

        @Schema(description = "Enum value with error status", example = "UNAUTHORIZED")
        ErrorCode errorCode
) {
}
