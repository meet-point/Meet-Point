package ru.meetpoint.eventservice.api;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.security.starter.response.OperationResponse;
import ru.meetpoint.security.starter.response.ValidationErrorResponse;

import java.util.Set;
import java.util.UUID;

@Tag(
        name = "Event API",
        description = "Endpoints for retrieving event information and managing events"
)
@Validated
@RequestMapping(path = "/api/v1/events", produces = "application/json")
public interface EventApi {

    @Operation(
            summary = "Get all events (paginated)",
            description = "Retrieves a paginated list of all available events",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Events retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
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
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Page<EventShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary = "Get events for main page",
            description = "Retrieves events for main page",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Main page events retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Set.class)
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
    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    Set<EventShortResponse> getForMainPage();

    @Operation(
            summary = "Search events by criteria",
            description = "Searches events based on provided search criteria",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Events matching criteria retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid search criteria",
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
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Page<EventShortResponse> getByCriteria(
            @Parameter(description = "Search criteria for filtering events", required = true)
            @Valid @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @Operation(
            summary = "Get event by ID",
            description = "Retrieves detailed information about a specific event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EventDetailedResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
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
    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventDetailedResponse getById(
            @Parameter(
                    description = "ID of the event to retrieve",
                    example = "f0441362-8ebc-4ea9-b22c-67c174d3e262",
                    required = true
            )
            @PathVariable("eventId") UUID eventId
    );

    @Operation(
            summary = "Create new event",
            description = "Creates a new event. Requires MANAGER role with organization or ADMINISTRATOR/OWNER role",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event created successfully",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveAnOrganization(#authPrincipal.userId)) " +
            "or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse create(
            @Parameter(description = "Event data", required = true)
            @Valid @RequestBody EventRequest eventRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Update event",
            description = "Updates an existing event. Manager must have rights to manage this event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event updated successfully",
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
                            responseCode = "403",
                            description = "Forbidden - insufficient permissions",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
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
    @PreAuthorize("(@managementServiceImpl.isUserHaveRightsToManageEvents(#eventId, #authPrincipal.userId) " +
            "and hasRole('MANAGER')) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @Parameter(
                    description = "ID of the event to update",
                    example = "f0441362-8ebc-4ea9-b22c-67c174d3e262",
                    required = true
            )
            @PathVariable("eventId") UUID eventId,

            @Parameter(description = "Updated event data", required = true)
            @Valid @RequestBody EventRequest eventRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Delete event",
            description = "Deletes an event. Manager must have rights to manage this event",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event deleted successfully",
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
                            description = "Event not found",
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
    @PreAuthorize("(@managementServiceImpl.isUserHaveRightsToManageEvents(#eventId, #authPrincipal.userId) " +
            "and hasRole('MANAGER')) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @Parameter(
                    description = "ID of the event to delete",
                    example = "f0441362-8ebc-4ea9-b22c-67c174d3e262",
                    required = true
            )
            @PathVariable("eventId") UUID eventId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
