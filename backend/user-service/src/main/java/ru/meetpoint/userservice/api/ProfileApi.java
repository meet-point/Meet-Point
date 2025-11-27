package ru.meetpoint.userservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.userservice.data.dto.request.FriendInvitationRequest;
import ru.meetpoint.userservice.data.dto.request.UpdatePasswordRequest;
import ru.meetpoint.userservice.data.dto.request.UpdateProfileInfoRequest;
import ru.meetpoint.userservice.data.dto.response.FriendInvitationResponse;
import ru.meetpoint.userservice.data.dto.response.FriendShortResponse;
import ru.meetpoint.userservice.data.dto.response.OperationResponse;
import ru.meetpoint.userservice.data.dto.response.ProfileResponse;

import java.util.Set;
import java.util.UUID;

@Tag(
        name = "Profile API",
        description = "Endpoints for managing user profile, password, and friend relationships"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/v1/profile", produces = "application/json")
public interface ProfileApi {

    @Operation(
            summary = "Get current user profile",
            description = "Returns the authenticated user's profile information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile received successfully",
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ProfileResponse getProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @Operation(
            summary = "Update profile information",
            description = "Updates the authenticated user's profile information including personal details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateProfileInformation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(description = "Profile update data", required = true)
            @Valid @RequestBody UpdateProfileInfoRequest updateProfileInfoRequest
    );

    @Operation(
            summary = "Delete user account",
            description = "The account of the authenticated user will be deleted. The account can be restored within " +
                    "30 days after deletion.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account deleted successfully",
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteAccount(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @Operation(
            summary = "Restore deleted account",
            description = "Restores a previously deleted user account within the retention period",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account restored successfully",
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
                            responseCode = "410",
                            description = "Account retention period has expired",
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
    @PostMapping("/restore")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse restoreAccount(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @Operation(
            summary = "Update password",
            description = "Changes the authenticated user's password. New password must meet security requirements.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data or passwords don't match",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updatePassword(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(description = "Password update data", required = true)
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    );

    @Operation(
            summary = "Get user's friends list",
            description = "Returns the list of confirmed friends for the authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friends list received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Set.class)
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    Set<FriendShortResponse> getUserFriends(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @Operation(
            summary = "Send friend invitation",
            description = "Sends a friend invitation to another user. The recipient will receive a friend request.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friend invitation sent successfully",
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
    @PostMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse addNewFriend(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(description = "Friend invitation data", required = true)
            @Valid @RequestBody FriendInvitationRequest friendInvitationRequest
    );

    @Operation(
            summary = "Get pending friend requests",
            description = "Returns the list of pending friend invitations received by the authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friend requests received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Set.class)
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
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/friends/requests")
    @ResponseStatus(HttpStatus.OK)
    Set<FriendInvitationResponse> getFriendRequests(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @Operation(
            summary = "Confirm or decline friend invitation",
            description = "Confirms (accepts) or declines a pending friend invitation from another user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friend invitation processed successfully",
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
                            description = "User or friend request not found",
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
    @PostMapping("/friends/{friend-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse confirmFriendInvitation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter(
                    description = "ID of the user sending the friend invitation",
                    example = "f5ed8a67-53eb-461d-998d-93a60433df9c",
                    required = true
            )
            @PathVariable("friend-id") UUID friendId,

            @Parameter(
                    description = "Flag to confirm (true) or decline (false) the invitation",
                    example = "true",
                    required = true
            )
            @RequestParam("isConfirmed") boolean isConfirmed
    );

    @Operation(
            summary = "Remove friend",
            description = "Removes a user from the authenticated user's friends list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friend removed successfully",
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
                            description = "User or friend relationship not found",
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
    @DeleteMapping("/friends/{friend-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteFriend(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("friend-id") UUID friendId
    );

}
