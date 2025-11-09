package ru.meetpoint.eventservice.data.dto.response.organization;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrganizationShortResponse(
        UUID organizationId,
        String label,
        String preview,
        String city,
        String address
) {
}
