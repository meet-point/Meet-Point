package ru.meetpoint.eventservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.location.LocationRequest;
import ru.meetpoint.eventservice.data.dto.response.location.LocationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.location.LocationAdditional;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.exception.BadRequestException;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocationDataMapper {

    private final EventDataMapper eventDataMapper;

    public LocationData toEntity(LocationRequest locationRequest) {
        return LocationData.builder()
                .label(locationRequest.label())
                .preview(locationRequest.preview())
                .description(locationRequest.description())
                .city(locationRequest.city())
                .address(locationRequest.address())
                .publicStatus(locationRequest.status())
                .build();
    }

    public void toUpdatedEntity(LocationData locationData, LocationRequest locationRequest) {
        locationData.setLabel(locationRequest.label());
        locationData.setPreview(locationRequest.preview());
        locationData.setDescription(locationRequest.description());
        locationData.setCity(locationRequest.city());
        locationData.setAddress(locationRequest.address());
        locationData.setPublicStatus(locationRequest.status());
    }

    public LocationShortResponse toShortResponse(LocationData locationData) {
        return LocationShortResponse.builder()
                .locationId(locationData.getLocationId())
                .label(locationData.getLabel())
                .preview(locationData.getPreview())
                .city(locationData.getCity())
                .address(locationData.getCity())
                .status(locationData.getPublicStatus())
                .build();
    }

    public LocationDetailedResponse toDetailedResponse(LocationData locationData) {

        if (locationData == null) {
            throw new BadRequestException("Location data cannot be null!");
        }

        LocationAdditional locationAdditional = locationData.getLocationAdditional();
        if (locationAdditional == null) {
            throw new BadRequestException("Location additional data must be loaded from location data!");
        }

        Set<EventData> eventDataSet = locationData.getEvents();
        if (eventDataSet == null) {
            throw new BadRequestException("Location's events must be loaded from location data!");
        }

        OrganizationData organizationData = locationData.getOrganizationData();
        if (organizationData == null) {
            throw new BadRequestException("Organization data must be loaded from location data!");
        }

        return LocationDetailedResponse.builder()
                .locationId(locationData.getLocationId())
                .label(locationData.getLabel())
                .preview(locationData.getPreview())
                .description(locationData.getDescription())
                .city(locationData.getCity())
                .address(locationData.getAddress())
                .status(locationData.getPublicStatus())
                .eventsDescription(locationAdditional.getEventsDescription())
                .completedEventsDescription(locationAdditional.getCompletedEventsDescription())
                .organizationDescription(locationAdditional.getOrganizationDescription())
                .events(eventDataSet.stream()
                        .map(eventDataMapper::toShortResponse)
                        .collect(Collectors.toSet()))
                .organization(OrganizationShortResponse.builder()
                        .organizationId(organizationData.getOrganizationId())
                        .label(organizationData.getLabel())
                        .preview(organizationData.getPreview())
                        .city(organizationData.getCity())
                        .address(organizationData.getAddress())
                        .build())
                .managementInfo(ManagementInfoResponse.builder()
                        .createdAt(locationAdditional.getCreatedAt())
                        .creatorId(locationAdditional.getCreatorId())
                        .lastUpdateBy(locationAdditional.getLastUpdateBy())
                        .updatedAt(locationAdditional.getLastUpdateAt())
                        .build())
                .build();
    }
}
