package ru.meetpoint.eventservice.data.dto.request.location;

import lombok.Builder;
import ru.meetpoint.eventservice.data.enums.LocationPublicStatus;

import java.util.Set;
import java.util.UUID;

@Builder
public record LocationRequest(
        String label,
        String preview,
        String description,
        String city,
        String address,
        LocationPublicStatus status,
        String eventsDescription,
        String completedEventsDescription,
        String organizationDescription,
        UUID organizationId
) {
}
