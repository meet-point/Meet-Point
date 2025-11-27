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
import org.springframework.web.bind.annotation.*;
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.security.starter.response.OperationResponse;
import ru.meetpoint.security.starter.response.ValidationErrorResponse;

import java.util.Set;
import java.util.UUID;

@Tag(
        name = "Location API",
        description = "Endpoints for managing event locations"
)
@RequestMapping(path = "/api/v1/location", produces = "application/json")
public interface LocationApi {

    @Operation(
            summary = "Get all locations (paginated)",
            description = "Receives a paginated list of all available locations",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations received successfully",
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<LocationShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary = "Get event locations for main page",
            description = "Receives event locations for main page",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Main page locations received successfully",
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
    Set<LocationShortResponse> getForMainPage();

    @Operation(
            summary = "Search event locations by criteria",
            description = "Searches event locations based on provided search criteria",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations matching criteria retrieved successfully",
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
    Page<LocationShortResponse> getByCriteria(
            @Parameter(description = "Search criteria for filtering locations", required = true)
            @Valid @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @Operation(
            summary = "Get location by ID",
            description = "Receives detailed information about a specific location",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location received successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LocationDetailedResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location not found",
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
    @GetMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    LocationDetailedResponse getById(
            @Parameter(
                    description = "ID of the location to receive",
                    example = "db9a4b57-a017-44dd-9c24-97e84636bf3b",
                    required = true
            )
            @PathVariable("locationId") UUID locationId
    );

    @Operation(
            summary = "Create new location",
            description = "Creates a new location. Requires MANAGER role with organization or ADMINISTRATOR/OWNER role",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Location created successfully",
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
            @Parameter(description = "Location data", required = true)
            @Valid @RequestBody LocationRequest locationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Update location",
            description = "Updates an existing location. Manager must have rights to manage this location",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location updated successfully",
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
                            description = "Location not found",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageLocations(#locationId, " +
            "#authPrincipal.userId)) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PatchMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @Parameter(
                    description = "ID of the location to update",
                    example = "db9a4b57-a017-44dd-9c24-97e84636bf3b",
                    required = true
            )
            @PathVariable("locationId") UUID locationId,

            @Parameter(description = "Updated location data", required = true)
            @Valid @RequestBody LocationRequest locationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Delete location",
            description = "Deletes a location. Manager must have rights to manage this location",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location deleted successfully",
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
                            description = "Location not found",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageLocations(#locationId, " +
            "#authPrincipal.userId)) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @Parameter(
                    description = "ID of the location to delete",
                    example = "db9a4b57-a017-44dd-9c24-97e84636bf3b",
                    required = true
            )
            @PathVariable("locationId") UUID locationId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
