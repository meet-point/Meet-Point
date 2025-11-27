package ru.meetpoint.authservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.authservice.data.dto.request.form.LoginForm;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.dto.response.AccessTokenResponse;
import ru.meetpoint.authservice.data.dto.response.EmailAvailabilityResponse;
import ru.meetpoint.authservice.data.dto.response.RegistrationResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.security.starter.response.OperationResponse;
import ru.meetpoint.security.starter.response.ValidationErrorResponse;

@Tag(
        name = "Authentication API",
        description = "Endpoints for login, registration, email verification and tokens management"
)
@Validated
@RequestMapping(path = "/api/v1/auth", produces = "application/json")
public interface AuthenticationApi {

    @Operation(
            summary = "Register a new user",
            description = "Regisres a new user, sends verification code to email and set encryptes-email-cookie",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User successfully registered",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegistrationResponse.class)
                            )
                    ),
                    @ApiResponse (
                            responseCode = "400",
                            description = """
                                    Invalid input data
                                    `INVALID_INPUT_DATA`
                                    `PASSWORD_DO_NOT_MATCH`
                                    """,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )

                    ),
                    @ApiResponse (
                            responseCode = "403",
                            description = "Can not register user that is already authenticated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )

                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )

                    )
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    RegistrationResponse register(

            @Parameter(description = "User registration form", required = true)
            @Valid
            @RequestBody RegistrationForm registrationForm,

            @Parameter(hidden = true) HttpServletResponse httpServletResponse
    );

    @Operation(
            summary = "Login",
            description = "Logs in a user and returns an access token. Also sets a refresh token cookie.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccessTokenResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data - null or empty value",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = """
                                    ACCOUNT_NOT_VERIFIED
                                    ACCOUNT_BANNED
                                    """,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    AccessTokenResponse login(

            @Parameter(description = "User login form with email and password")
            @Valid
            @RequestBody LoginForm loginForm,

            @Parameter(hidden = true) HttpServletResponse httpServletResponse
    );

    @Operation(
            summary = "Logout",
            description = "Logs out the current user by invalidating tokens and clearing the refresh cookie",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Logout successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized user can not logout",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse logout(

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(hidden = true) HttpServletResponse httpServletResponse
    );

    @Operation(
            summary = "Verify a user",
            description = "Verifies a user by email verification code",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User verified successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = """
                                    EMAIL_CODE_EXPIRED
                                    EMAIL_CODE_INVALID
                                    """,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Can not verify already verified user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse verify(

            @Parameter(
                    description = "Encrypted email value, stored in cookie",
                    required = true,
                    example = "...",
                    schema = @Schema(type = "string", format = "string")
            )
            @CookieValue("email") String  encryptedEmail,

            @Parameter(
                    description = "Verification code sent to user's email",
                    required = true,
                    example = "123456",
                    schema = @Schema(type = "string", format = "string")
            )
            @NotBlank(message = "Verification code should not be null")
            @RequestParam("code") String code
    );

    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token in cookie",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Access token refreshed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccessTokenResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid or expired refresh token",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    AccessTokenResponse refreshAccessToken(

            @Parameter(description = "Refresh token stored in cookie")
            @CookieValue("refresh_token") String refreshToken,

            @Parameter(hidden = true) HttpServletResponse httpServletResponse
    );

    @Operation(
            summary = "Check is email available",
            description = "Checks: given email is already used by another user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email availability checked",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email is empty or in invalid format",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/check_email")
    @ResponseStatus(HttpStatus.OK)
    EmailAvailabilityResponse checkEmailIsAvailable(

            @Parameter(
                    description = "Email address to check",
                    required = true,
                    example = "example@example.com",
                    schema = @Schema(type = "string", format = "email")
            )
            @RequestParam String email
    );

}
