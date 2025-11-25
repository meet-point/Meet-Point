package ru.meetpoint.eventservice.data.dto.response.organization;

import lombok.Builder;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;

import java.util.Set;
import java.util.UUID;

@Builder
public record OrganizationDetailedResponse(
        UUID organizationId,
        String label,
        String preview,
        String description,
        String city,
        String address,
        String contactEmail,
        String contactPhone,
        String eventsDescription,
        String completedEventsDescription,
        String locationsDescription,
        Set<EventShortResponse> events,
        Set<LocationShortResponse> locations,
        ManagementInfoResponse managementInfo
) {
}
