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
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.ApiErrorResponse;
import ru.meetpoint.security.starter.response.OperationResponse;
import ru.meetpoint.security.starter.response.ValidationErrorResponse;

import java.util.Set;
import java.util.UUID;

@Tag(
        name = "Organization API",
        description = "Endpoints for managing organizations and their managers"
)
@Validated
@RequestMapping(path = "/api/v1/organization", produces = "application/json")
public interface OrganizationApi {

    @Operation(
            summary = "Get all organizations (paginated)",
            description = "Retrieves a paginated list of all available organizations",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizations retrieved successfully",
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
    Page<OrganizationShortResponse> getAll(
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary = "Get organizations for main page",
            description = "Retrieves featured/main page organizations",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Main page organizations retrieved successfully",
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
    Set<OrganizationShortResponse> getForMainPage();

    @Operation(
            summary = "Search organizations by criteria",
            description = "Searches organizations based on provided search criteria",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizations matching criteria retrieved successfully",
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
    Page<OrganizationShortResponse> getByCriteria(
            @Parameter(description = "Search criteria for filtering organizations", required = true)
            @Valid @RequestBody UnifiedSearchCriteriaRequest searchCriteriaRequest
    );

    @Operation(
            summary = "Get organization by ID",
            description = "Retrieves detailed information about a specific organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organization retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationDetailedResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Organization not found",
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
    @GetMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDetailedResponse getById(
            @Parameter(
                    description = "ID of the organization to retrieve",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId
    );

    @Operation(
            summary = "Create new organization",
            description = "Creates a new organization. Requires MANAGER role with appropriate permissions or " +
                    "ADMINISTRATOR/OWNER role",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Organization created successfully",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl" +
            ".isUserHaveRightsToCreateOrganization(#authPrincipal.userId)) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse create(
            @Parameter(description = "Organization data", required = true)
            @Valid @RequestBody OrganizationRequest organizationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Update organization",
            description = "Updates an existing organization. Manager must have rights to manage this organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organization updated successfully",
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
                            description = "Organization not found",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PatchMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse update(
            @Parameter(
                    description = "ID of the organization to update",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(description = "Updated organization data", required = true)
            @Valid @RequestBody OrganizationRequest organizationRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Delete organization",
            description = "Deletes an organization. Manager must have rights to manage this organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organization deleted successfully",
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
                            description = "Organization not found",
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
    @PreAuthorize("(hasRole('MANAGER') and @managementServiceImpl.isUserHaveRightsToManageOrganization(" +
            "#organizationId, #authPrincipal.userId)) or hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @DeleteMapping("/{organization-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse delete(
            @Parameter(
                    description = "ID of the organization to delete",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Get manager from organization",
            description = "Retrieves information about a specific manager within an organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Manager retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManagerResponse.class)
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
                            description = "Organization or manager not found",
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
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @GetMapping("/{organization-id}/managers/{manager-id}")
    @ResponseStatus(HttpStatus.OK)
    ManagerResponse getManager(
            @Parameter(
                    description = "ID of the organization",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(
                    description = "ID of the manager to retrieve",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @PathVariable("manager-id") UUID managerId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Add manager to organization",
            description = "Adds a new manager to the organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Manager added successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data or manager already exists",
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
                            description = "Organization or user not found",
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
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @PostMapping("/{organization-id}/managers")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse addManagerToOrganization(
            @Parameter(
                    description = "ID of the organization",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(description = "Manager data", required = true)
            @Valid @RequestBody ManagerRequest managerRequest,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Remove manager from organization",
            description = "Removes a manager from the organization",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Manager removed successfully",
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
                            description = "Organization or manager not found",
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
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('OWNER')")
    @DeleteMapping("/{organization-id}/managers/{manager-id}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteManager(
            @Parameter(
                    description = "ID of the organization",
                    example = "49765968-25fc-4707-a770-28940eaac1fd",
                    required = true
            )
            @PathVariable("organization-id") UUID organizationId,

            @Parameter(
                    description = "ID of the manager to remove",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @PathVariable("manager-id") UUID managerId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );
}
