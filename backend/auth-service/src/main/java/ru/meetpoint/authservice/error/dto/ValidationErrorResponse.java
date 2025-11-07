package ru.meetpoint.authservice.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.ErrorCode;

import java.util.List;

@Builder
@Schema(description = "Response containing validation errors.")
public record ValidationErrorResponse(

        @Schema(description = "HTTP status code", example = "400")
        int code,

        @Schema(description = "Error message", example = "Validation failed")
        String exceptionMessage,

        @Schema(description = "Enum value with error status", example = "EMAIL_ALREADY_EXIST")
        ErrorCode errorCode,

        @Schema(description = "List of field validation errors")
        List<ValidationError> validationErrors
) {

    @Builder
    @Schema(description = "Validation error for a specific field.")
    public record ValidationError(

            @Schema(description = "Name of the invalid field.", example = "email")
            String field,

            @Schema(description = "Error message for the field.", example = "Email must be unique!")
            String message
    ) {
    }
}
