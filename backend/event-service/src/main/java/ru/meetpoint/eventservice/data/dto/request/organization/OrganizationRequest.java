package ru.meetpoint.eventservice.data.dto.request.organization;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record OrganizationRequest(
        String label,
        String preview,
        String description,
        String city,
        String address,
        String contactEmail,
        String contactPhone,
        String eventsDescription,
        String completedEventsDescription,
        String locationsDescription
) {
}
