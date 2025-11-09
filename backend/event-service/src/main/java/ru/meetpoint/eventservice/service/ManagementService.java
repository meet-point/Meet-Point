package ru.meetpoint.eventservice.service;

import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;

import java.util.UUID;

public interface ManagementService {

    boolean isUserHaveAnOrganization(UnifiedAuthPrincipal authPrincipal);

    boolean isUserHaveRightsToCreateOrganization(UnifiedAuthPrincipal authPrincipal);

    boolean isUserHaveRightsToManageEvents(UUID eventId, UnifiedAuthPrincipal authPrincipal);

    boolean isUserHaveRightsToManageLocations(UUID locationId, UnifiedAuthPrincipal authPrincipal);

    boolean isUserHaveRightsToManageOrganization(UUID organizationId, UnifiedAuthPrincipal authPrincipal);
}
