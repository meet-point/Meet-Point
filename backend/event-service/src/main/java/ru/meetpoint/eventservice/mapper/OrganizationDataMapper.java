package ru.meetpoint.eventservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationAdditional;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.error.exception.BadRequestException;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrganizationDataMapper {

    private final LocationDataMapper locationDataMapper;

    private final EventDataMapper eventDataMapper;

    public OrganizationData toEntity(OrganizationRequest organizationRequest) {
        return OrganizationData.builder()
                .label(organizationRequest.label())
                .preview(organizationRequest.preview())
                .description(organizationRequest.description())
                .city(organizationRequest.city())
                .address(organizationRequest.address())
                .build();
    }

    public void toUpdatedEntity(OrganizationData organizationData, OrganizationRequest organizationRequest) {
        if (organizationRequest.label() != null && !organizationRequest.label().isEmpty()) {
            organizationData.setLabel(organizationRequest.label());
        }

        if (organizationRequest.preview() != null && !organizationRequest.preview().isEmpty()) {
            organizationData.setPreview(organizationRequest.preview());
        }

        if (organizationRequest.description() != null && !organizationRequest.description().isEmpty()) {
            organizationData.setDescription(organizationRequest.description());
        }

        if (organizationRequest.city() != null && !organizationRequest.city().isEmpty()) {
            organizationData.setCity(organizationRequest.city());
        }

        if (organizationRequest.address() != null && !organizationRequest.address().isEmpty()) {
            organizationData.setAddress(organizationRequest.address());
        }
    }

    public OrganizationShortResponse toShortResponse(OrganizationData organizationData) {
        return OrganizationShortResponse.builder()
                .organizationId(organizationData.getOrganizationId())
                .label(organizationData.getLabel())
                .preview(organizationData.getPreview())
                .city(organizationData.getCity())
                .address(organizationData.getAddress())
                .build();
    }

    public OrganizationDetailedResponse toDetailedResponse(OrganizationData organizationData) {

        if (organizationData == null) {
            throw new BadRequestException("Organization data cannot be null!");
        }

        OrganizationAdditional organizationAdditional = organizationData.getOrganizationAdditional();
        if (organizationAdditional == null) {
            throw new BadRequestException("Organization additional data must be loaded from organization data!");
        }

        Set<LocationData> locationDataSet = organizationData.getLocations();
        if (locationDataSet == null) {
            throw new BadRequestException("Organization's locations must be loaded from organization data!");
        }

        Set<EventData> eventDataSet = locationDataSet.stream()
                .flatMap(locationData -> locationData.getEvents().stream())
                .collect(Collectors.toSet());

        return OrganizationDetailedResponse.builder()
                .organizationId(organizationData.getOrganizationId())
                .label(organizationData.getLabel())
                .preview(organizationData.getPreview())
                .description(organizationData.getDescription())
                .city(organizationData.getCity())
                .address(organizationData.getAddress())
                .contactEmail(organizationAdditional.getContactEmail())
                .contactPhone(organizationAdditional.getContactPhone())
                .eventsDescription(organizationAdditional.getEventsDescription())
                .completedEventsDescription(organizationAdditional.getCompletedEventsDescription())
                .locationsDescription(organizationAdditional.getLocationsDescription())
                .events(eventDataSet.stream()
                        .map(eventDataMapper::toShortResponse)
                        .collect(Collectors.toSet()))
                .locations(locationDataSet.stream()
                        .map(locationDataMapper::toShortResponse)
                        .collect(Collectors.toSet()))
                .managementInfo(ManagementInfoResponse.builder()
                        .creatorId(organizationAdditional.getCreatorId())
                        .createdAt(organizationAdditional.getCreatedAt())
                        .lastUpdateBy(organizationAdditional.getLastUpdateBy())
                        .updatedAt(organizationAdditional.getLastUpdateAt())
                        .build())
                .build();
    }
}
