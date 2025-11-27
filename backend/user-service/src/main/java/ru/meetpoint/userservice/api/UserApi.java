package ru.meetpoint.userservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.userservice.data.dto.request.CreateUserAccountRequest;
import ru.meetpoint.userservice.data.dto.request.CreateUserComplaintRequest;
import ru.meetpoint.userservice.data.dto.request.UpdateUserRoleRequest;
import ru.meetpoint.userservice.data.dto.response.OperationResponse;
import ru.meetpoint.userservice.data.dto.response.ProfileResponse;
import ru.meetpoint.userservice.data.dto.response.UserAccountResponse;
import ru.meetpoint.userservice.data.dto.response.UserComplaintResponse;

import java.util.UUID;

@Tag(
        name = "User API",
        description = "Endpoints for managing user accounts"
)
@RequestMapping(path = "/api/v1/users", produces = "application/json")
public interface UserApi {

    @Operation(
            summary = "Get all users (paginated)",
            description = "Retrieves a paginated list of all users. Accessible to moderators, administrators and owner.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<UserAccountResponse> getAllUsers(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Create new user account",
            description = "Creates a new user account. No authentication required.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User account created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data (validation error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User with this email already exists",
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse createUserAccount(
            @Parameter(description = "User account creation data", required = true)
            @Valid @RequestBody CreateUserAccountRequest createUserAccountRequest
    );

    @Operation(
            summary = "Get all complaints (paginated)",
            description = "Retrieves a paginated list of all complaints. Accessible to moderators, administrators and owner.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Complaints received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping("/complaints")
    @ResponseStatus(HttpStatus.OK)
    Page<UserComplaintResponse> getAllComplaints(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Get all complaints for specific user (paginated)",
            description = "Retrieves a paginated list of complaints filed against a specific user. Accessible to " +
                    "moderators, administrators and owners.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User complaints received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping("/complaints/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    Page<UserComplaintResponse> getAllUserComplaints(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user to fetch complaints for",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("user-id") UUID userId,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Get user profile by ID",
            description = "Receives the profile information for a specific user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User profile received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
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
    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    ProfileResponse getUserProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user to retrieve",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("user-id") UUID userId
    );

    @Operation(
            summary = "Create complaint against user",
            description = "Files a complaint against a specific user. The authenticated user is the complaint author.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Complaint created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
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
    @PostMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse createUserComplaint(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user to file complaint against",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("user-id") UUID userId,

            @Parameter(description = "Complaint data", required = true)
            @Valid @RequestBody CreateUserComplaintRequest createUserComplaintRequest
    );

    @Operation(
            summary = "Update user banned status",
            description = "Bans or unbans a user account. Accessible to moderators, administrators and owner.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Banned status updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping("/{user-id}/ban")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateBannedStatus(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user to ban/unban",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("user-id") UUID userId,

            @Parameter(
                    description = "Flag to ban (true) or unban (false) the user",
                    example = "true",
                    required = true
            )
            @RequestParam("isBanned") boolean isBanned
    );

    @Operation(
            summary = "Update user role",
            description = "Assigns or revokes a specific role for a user. Accessible to moderators, administrators and owner.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User role updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping("/{user-id}/role")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateUserRole(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user whose role is being updated",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("user-id") UUID userId,

            @Parameter(description = "Role update data", required = true)
            @Valid @RequestBody UpdateUserRoleRequest updateUserRoleRequest
    );
}
