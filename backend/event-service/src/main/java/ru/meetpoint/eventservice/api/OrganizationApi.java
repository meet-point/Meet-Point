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
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;

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
    Set<OrganizationShortResponse> getAllForMainPage();

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Page<OrganizationShortResponse> getByCriteria(
            @Valid
            @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @GetMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDetailedResponse getById(
            @PathVariable("organizationId") UUID organizationId
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
    @PatchMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @PathVariable("organizationId") UUID organizationId,

            @Valid
            @RequestBody OrganizationRequest organizationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @PathVariable("organizationId") UUID organizationId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
