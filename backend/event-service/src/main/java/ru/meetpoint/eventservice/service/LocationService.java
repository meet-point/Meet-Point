package ru.meetpoint.eventservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;

import java.util.Set;
import java.util.UUID;

public interface LocationService {
    Page<LocationShortResponse> getAll(Pageable pageable);

    Set<LocationShortResponse> getForMainPage();

    Page<LocationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest);

    LocationDetailedResponse getById(UUID locationId);

    OperationResponse create(LocationRequest locationRequest, UUID userId);

    OperationResponse update(UUID locationId, LocationRequest locationRequest, UUID userId);

    OperationResponse delete(UUID locationId, UUID userId);
}
