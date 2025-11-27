package ru.meetpoint.userservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.userservice.data.dto.request.CreateUserAccountRequest;
import ru.meetpoint.userservice.data.dto.request.CreateUserComplaintRequest;
import ru.meetpoint.userservice.data.dto.request.UpdateUserRoleRequest;
import ru.meetpoint.userservice.data.dto.response.OperationResponse;
import ru.meetpoint.userservice.data.dto.response.ProfileResponse;
import ru.meetpoint.userservice.data.dto.response.UserAccountResponse;
import ru.meetpoint.userservice.data.dto.response.UserComplaintShortResponse;

import java.util.UUID;

@RequestMapping(path = "/api/v1/users", produces = "application/json")
public interface UserApi {

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<UserAccountResponse> getAllUsers(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @ParameterObject
            Pageable pageable
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse createUserAccount(
            @Parameter
            @RequestBody CreateUserAccountRequest createUserAccountRequest
    );

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping("/complaints")
    @ResponseStatus(HttpStatus.OK)
    Page<UserComplaintShortResponse> getAllComplaints(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @ParameterObject
            Pageable pageable
    );

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping("/complaints/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    Page<UserComplaintShortResponse> getAllUserComplaints(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("user-id") UUID userId,

            @ParameterObject
            Pageable pageable
    );

    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    ProfileResponse getUserProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("user-id") UUID userId
    );

    @PostMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse createUserComplaint(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("user-id") UUID userId,

            @Parameter
            @RequestBody CreateUserComplaintRequest createUserComplaintRequest
    );

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping("/{user-id}/ban")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateBannedStatus(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("user-id") UUID userId,

            @Parameter
            @RequestParam("isBanned") boolean isBanned
    );

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping("/{user-id}/role")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateUserRole(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal unifiedAuthPrincipal,

            @Parameter
            @PathVariable("user-id") UUID userId,

            @Parameter
            @RequestBody UpdateUserRoleRequest updateUserRoleRequest
    );
}
