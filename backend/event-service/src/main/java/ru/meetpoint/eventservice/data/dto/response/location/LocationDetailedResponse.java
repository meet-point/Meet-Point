package ru.meetpoint.eventservice.data.dto.response.location;

import lombok.Builder;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.enums.LocationPublicStatus;

import java.util.Set;
import java.util.UUID;

@Builder
public record LocationDetailedResponse(
        UUID locationId,
        String label,
        String preview,
        String description,
        String city,
        String address,
        LocationPublicStatus status,
        String eventsDescription,
        String completedEventsDescription,
        String organizationDescription,
        Set<EventShortResponse> events,
        OrganizationShortResponse organization,
        ManagementInfoResponse managementInfo
) {
}
