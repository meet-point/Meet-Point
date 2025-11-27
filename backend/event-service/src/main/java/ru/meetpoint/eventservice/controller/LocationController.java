package ru.meetpoint.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.meetpoint.eventservice.api.LocationApi;
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.service.LocationService;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LocationController implements LocationApi {

    private final LocationService locationService;

    @Override
    public Page<LocationShortResponse> getAll(Pageable pageable) {
        return locationService.getAll(pageable);
    }

    @Override
    public Set<LocationShortResponse> getForMainPage() {
        return locationService.getForMainPage();
    }

    @Override
    public Page<LocationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        return locationService.getByCriteria(searchCriteriaRequest);
    }

    @Override
    public LocationDetailedResponse getById(UUID locationId) {
        return locationService.getById(locationId);
    }

    @Override
    public OperationResponse create(LocationRequest locationRequest, UnifiedAuthPrincipal authPrincipal) {
        return locationService.create(locationRequest, authPrincipal.getUserId());
    }

    @Override
    public OperationResponse update(UUID locationId, LocationRequest locationRequest, UnifiedAuthPrincipal authPrincipal) {
        return locationService.update(locationId, locationRequest, authPrincipal.getUserId());
    }

    @Override
    public OperationResponse delete(UUID locationId, UnifiedAuthPrincipal authPrincipal) {
        return locationService.delete(locationId, authPrincipal.getUserId());
    }
}
