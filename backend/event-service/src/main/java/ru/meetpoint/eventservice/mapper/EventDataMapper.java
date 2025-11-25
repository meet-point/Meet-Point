package ru.meetpoint.eventservice.mapper;

import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.entity.event.EventAdditional;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.error.exception.BadRequestException;

@Component
public class EventDataMapper {

    public EventData toEntity(EventRequest request) {
        return EventData.builder()
                .label(request.label())
                .preview(request.preview())
                .description(request.description())
                .dateTime(request.dateTime())
                .cost(request.cost())
                .maxAllowedPeople(request.maxAllowedPeople())
                .registeredNow(0)
                .accessStatus(request.accessStatus())
                .publishStatus(request.eventPublishStatus())
                .build();
    }

    public void toUpdatedEntity(EventData eventData, EventRequest request) {
        eventData.setLabel(request.label());
        eventData.setPreview(request.preview());
        eventData.setDescription(request.description());
        eventData.setDateTime(request.dateTime());
        eventData.setCost(request.cost());
        eventData.setMaxAllowedPeople(request.maxAllowedPeople());
        eventData.setAccessStatus(request.accessStatus());
        eventData.setPublishStatus(request.eventPublishStatus());
    }

    public EventShortResponse toShortResponse(EventData eventData) {
        return EventShortResponse.builder()
                .eventId(eventData.getEventId())
                .label(eventData.getLabel())
                .preview(eventData.getPreview())
                .accessStatus(eventData.getAccessStatus())
                .publishStatus(eventData.getPublishStatus())
                .categoryLabel(eventData.getCategoryData().getLabel())
                .maxAllowedPeople(eventData.getMaxAllowedPeople())
                .registeredNow(eventData.getRegisteredNow())
                .cost(eventData.getCost())
                .build();
    }

    public EventDetailedResponse toDetailedResponse(EventData eventData) throws BadRequestException {

        if (eventData == null) {
            throw new BadRequestException("Event data cannot be null!");
        }

        EventAdditional eventAdditional = eventData.getEventAdditional();
        if (eventAdditional == null) {
            throw new BadRequestException("Event additional data must be loaded from event data!");
        }

        LocationData locationData = eventData.getLocationData();
        if (locationData == null) {
            throw new BadRequestException("Event additional data must be loaded from event data!");
        }

        OrganizationData organizationData = locationData.getOrganizationData();
        if (organizationData == null) {
            throw new BadRequestException("Organization data must be loaded from event data!");
        }

        return EventDetailedResponse.builder()
                .eventId(eventData.getEventId())
                .label(eventData.getLabel())
                .preview(eventData.getPreview())
                .description(eventData.getDescription())
                .accessStatus(eventData.getAccessStatus())
                .publishStatus(eventData.getPublishStatus())
                .categoryLabel(eventData.getCategoryData().getLabel())
                .dateTime(eventData.getDateTime())
                .maxAllowedPeople(eventData.getMaxAllowedPeople())
                .registeredNow(eventData.getRegisteredNow())
                .cost(eventData.getCost())
                .publishedAt(eventAdditional.getPublicationDate())
                .location(LocationShortResponse.builder()
                        .locationId(locationData.getLocationId())
                        .label(locationData.getLabel())
                        .preview(locationData.getPreview())
                        .city(locationData.getCity())
                        .address(locationData.getAddress())
                        .status(locationData.getPublicStatus())
                        .build())
                .organization(OrganizationShortResponse.builder()
                        .organizationId(organizationData.getOrganizationId())
                        .label(organizationData.getLabel())
                        .preview(organizationData.getPreview())
                        .city(organizationData.getCity())
                        .address(organizationData.getAddress())
                        .build())
                .managementInfo(ManagementInfoResponse.builder()
                        .createdAt(eventAdditional.getCreatedAt())
                        .creatorId(eventAdditional.getCreatorId())
                        .lastUpdateBy(eventAdditional.getLastUpdateBy())
                        .updatedAt(eventAdditional.getLastUpdateAt())
                        .build())
                .build();
    }
}
