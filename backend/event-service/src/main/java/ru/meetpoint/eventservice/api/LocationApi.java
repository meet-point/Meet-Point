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
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;

import java.util.UUID;

@RequestMapping(path = "/api/v1/location", produces = "application/json")
public interface LocationApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<LocationShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    LocationShortResponse getAllForMainPage();

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Page<LocationShortResponse> getByCriteria(
            @Valid
            @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @GetMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    LocationDetailedResponse getById(
            @PathVariable("locationId") UUID locationId
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveAnOrganization(#authPrincipal.userId)) " +
            "or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse create(
            @Valid
            @RequestBody LocationRequest locationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageLocations(#locationId, " +
            "#authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PatchMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @PathVariable("locationId") UUID locationId,

            @Valid
            @RequestBody LocationRequest locationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageLocations(#locationId, " +
            "#authPrincipal.userId)) or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @PathVariable("locationId") UUID locationId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
