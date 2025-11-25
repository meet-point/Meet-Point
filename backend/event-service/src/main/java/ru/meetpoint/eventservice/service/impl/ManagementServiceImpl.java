package ru.meetpoint.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meetpoint.eventservice.service.ManagementService;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {

    @Override
    public boolean isUserHaveAnOrganization(UnifiedAuthPrincipal authPrincipal) {
        return false;
    }

    @Override
    public boolean isUserHaveRightsToCreateOrganization(UnifiedAuthPrincipal authPrincipal) {
        return false;
    }

    @Override
    public boolean isUserHaveRightsToManageEvents(UUID eventId, UnifiedAuthPrincipal authPrincipal) {
        return false;
    }

    @Override
    public boolean isUserHaveRightsToManageLocations(UUID locationId, UnifiedAuthPrincipal authPrincipal) {
        return false;
    }

    @Override
    public boolean isUserHaveRightsToManageOrganization(UUID organizationId, UnifiedAuthPrincipal authPrincipal) {
        return false;
    }
}
