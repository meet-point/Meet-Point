package ru.meetpoint.eventservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

@Tag(
        name = "Event API",
        description = "Endpoints for getting basic information about events and managing them. "
)
@Validated
@RequestMapping(path = "/api/v1/events", produces = "application/json")
public interface EventApi {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Page<EventShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    Set<EventShortResponse> getForMainPage();

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Page<EventShortResponse> getByCriteria(
            @Valid
            @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventDetailedResponse getById(
            @PathVariable("eventId") UUID eventId
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveAnOrganization(#authPrincipal.userId)) " +
            "or hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse create(
            @Valid
            @RequestBody EventRequest eventRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(@managementServiceImpl.isUserHaveRightsToManageEvents(#eventId, #authPrincipal.userId) " +
            "and hasRole('MANAGER')) or hasRole('ADMIN') or hasRole('OWNER')")
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @PathVariable("eventId") UUID eventId,

            @Valid
            @RequestBody EventRequest eventRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("(@managementServiceImpl.isUserHaveRightsToManageEvents(#eventId, #authPrincipal.userId) " +
            "and hasRole('MANAGER')) or hasRole('ADMIN') or hasRole('OWNER')")
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @PathVariable("eventId") UUID eventId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

}
