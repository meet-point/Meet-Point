package ru.meetpoint.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;
import ru.meetpoint.eventservice.data.entity.location.LocationAdditional;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.exception.NotFoundException;
import ru.meetpoint.eventservice.mapper.LocationDataMapper;
import ru.meetpoint.eventservice.repository.location.LocationDataRepository;
import ru.meetpoint.eventservice.repository.organization.OrganizationDataRepository;
import ru.meetpoint.eventservice.service.LocationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationDataRepository locationDataRepository;

    private final LocationDataMapper locationDataMapper;
    private final OrganizationDataRepository organizationDataRepository;

    @Override
    public Page<LocationShortResponse> getAll(Pageable pageable) {
        return locationDataRepository
                .findAllPageable(pageable)
                .map(locationDataMapper::toShortResponse);
    }

    @Override
    public Set<LocationShortResponse> getForMainPage() {
        return locationDataRepository
                .findForMainPage().stream()
                .map(locationDataMapper::toShortResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<LocationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        Pageable pageable = PageRequest.of(
                searchCriteriaRequest.page(),
                searchCriteriaRequest.size(),
                Sort.by(searchCriteriaRequest.sortDirection())
        );

        return locationDataRepository
                .findAll(Specification.where(containsSearchRequest(searchCriteriaRequest.searchRequest())), pageable)
                .map(locationDataMapper::toShortResponse);
    }

    @Override
    @Transactional
    public LocationDetailedResponse getById(UUID locationId) {
        LocationData locationData = locationDataRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location data with id=%s was not found!".formatted(locationId)));

        if (locationData.getLocationAdditional() == null) {
            throw new NotFoundException("Location additional data with id=%s was not found!".formatted(locationId));
        }
        return locationDataMapper.toDetailedResponse(locationData);
    }

    @Override
    @Transactional
    public OperationResponse create(LocationRequest locationRequest, UUID userId) {
        LocationData locationData = locationDataMapper.toEntity(locationRequest);

        OrganizationData organizationData = organizationDataRepository.findById(locationRequest.organizationId())
                .orElseThrow(() -> new NotFoundException("Organization data with id=%s was not found!"
                        .formatted(locationRequest.organizationId()))
                );

        LocationAdditional locationAdditional = LocationAdditional.builder()
                .eventsDescription(locationRequest.eventsDescription())
                .completedEventsDescription(locationRequest.completedEventsDescription())
                .organizationDescription(locationRequest.organizationDescription())
                .creatorId(userId)
                .createdAt(Timestamp.from(Instant.now()))
                .locationData(locationData)
                .build();

        locationData.setLocationAdditional(locationAdditional);
        locationData.setOrganizationData(organizationData);

        locationData = locationDataRepository.save(locationData);

        return OperationResponse.builder()
                .entityId(locationData.getLocationId())
                .isSuccess(true)
                .build();
    }

    @Override
    @Transactional
    public OperationResponse update(UUID locationId, LocationRequest locationRequest, UUID userId) {
        LocationData locationData = locationDataRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location data with id=%s was not found!".formatted(locationId)));

        locationDataMapper.toUpdatedEntity(locationData, locationRequest);

        if (!locationData.getOrganizationData().getOrganizationId().equals(locationRequest.organizationId())) {
            OrganizationData organizationData = organizationDataRepository.findById(locationRequest.organizationId())
                    .orElseThrow(() -> new NotFoundException("Organization data with id=%s was not found!"
                            .formatted(locationRequest.organizationId()))
                    );
            locationData.setOrganizationData(organizationData);
        }

        LocationAdditional locationAdditional = locationData.getLocationAdditional();
        if (locationRequest.eventsDescription() != null && !locationRequest.eventsDescription().isEmpty()) {
            locationAdditional.setEventsDescription(locationRequest.eventsDescription());
        }

        if (locationRequest.completedEventsDescription() != null &&
                !locationRequest.completedEventsDescription().isEmpty()) {
            locationAdditional.setCompletedEventsDescription(locationRequest.completedEventsDescription());
        }

        if (locationRequest.organizationDescription() != null && !locationRequest.organizationDescription().isEmpty()) {
            locationAdditional.setOrganizationDescription(locationRequest.organizationDescription());
        }

        locationAdditional.setLastUpdateBy(userId);
        locationAdditional.setLastUpdateAt(Timestamp.from(Instant.now()));
        locationData.setLocationAdditional(locationAdditional);

        locationDataRepository.save(locationData);

        return OperationResponse.builder()
                .entityId(locationId)
                .isSuccess(true)
                .build();
    }

    @Override
    public OperationResponse delete(UUID locationId, UUID userId) {
        locationDataRepository.deleteById(locationId);
        return OperationResponse.builder()
                .isSuccess(true)
                .build();
    }

    private Specification<LocationData> containsSearchRequest(String searchRequest) {
        return (root, query, criteriaBuilder) -> (searchRequest == null || searchRequest.isEmpty()) ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("searchRequest")),
                        "%" + searchRequest.toLowerCase() + "%");
    }
}
