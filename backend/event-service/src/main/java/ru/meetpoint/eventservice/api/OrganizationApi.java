package ru.meetpoint.eventservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

@RequestMapping(path = "/api/v1/organization", produces = "application/json")
public interface OrganizationApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<OrganizationShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    Set<OrganizationShortResponse> getForMainPage();

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Page<OrganizationShortResponse> getByCriteria(
            @Valid
            @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @GetMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDetailedResponse getById(
            @PathVariable("organization-id") UUID organizationId
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl" +
            ".isUserHaveRightsToCreateOrganization(#authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse create(
            @Valid
            @RequestBody OrganizationRequest organizationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PatchMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @PathVariable("organization-id") UUID organizationId,

            @Valid
            @RequestBody OrganizationRequest organizationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @DeleteMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @GetMapping("/{organization-id}/managers/{manager-id}")
    @ResponseStatus(HttpStatus.OK)
    ManagerResponse getManager(
            @PathVariable("organization-id") UUID organizationId,

            @PathVariable("manager-id") UUID managerId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping("/{organization-id}/managers")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse addManagerToOrganization(
            @PathVariable("organization-id") UUID organizationId,

            @Valid
            @RequestBody ManagerRequest managerRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @DeleteMapping("/{organization-id}/managers/{manager-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteManager(
            @PathVariable("organization-id") UUID organizationId,

            @PathVariable("manager-id") UUID managerId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
