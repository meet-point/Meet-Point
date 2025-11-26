package ru.meetpoint.userservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.userservice.data.dto.request.FriendInvitationRequest;
import ru.meetpoint.userservice.data.dto.request.UpdatePasswordRequest;
import ru.meetpoint.userservice.data.dto.request.UpdateProfileInfoRequest;
import ru.meetpoint.userservice.data.dto.response.FriendInvitationResponse;
import ru.meetpoint.userservice.data.dto.response.FriendShortResponse;
import ru.meetpoint.userservice.data.dto.response.OperationResponse;
import ru.meetpoint.userservice.data.dto.response.ProfileDetailedResponse;

import java.util.Set;
import java.util.UUID;

@RequestMapping(path = "/api/v1/profile", produces = "application/json")
public interface ProfileApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ProfileDetailedResponse getProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateProfileInformation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @RequestBody UpdateProfileInfoRequest updateProfileInfoRequest
    );

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteAccount(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @PostMapping("/restore")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse restoreAccount(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updatePassword(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @RequestBody UpdatePasswordRequest updatePasswordRequest
    );

    @GetMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    Set<FriendShortResponse> getUserFriends(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @PostMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse addNewFriend(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @RequestBody FriendInvitationRequest friendInvitationRequest
    );

    @GetMapping("/friends/requests")
    @ResponseStatus(HttpStatus.OK)
    Set<FriendInvitationResponse> getFriendRequests(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal
    );

    @PostMapping("/friends/{friend-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse confirmFriendInvitation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("friend-id") UUID friendId,

            @Parameter
            @RequestParam("isConfirmed") boolean isConfirmed
    );

    @DeleteMapping("/friends/{friend-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteFriend(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("friend-id") UUID friendId
    );

}
