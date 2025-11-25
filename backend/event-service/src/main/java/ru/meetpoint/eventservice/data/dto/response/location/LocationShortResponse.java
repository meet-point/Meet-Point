package ru.meetpoint.eventservice.data.dto.response.location;

import lombok.Builder;
import ru.meetpoint.eventservice.data.enums.LocationPublicStatus;

import java.util.UUID;

@Builder
public record LocationShortResponse(
        UUID locationId,
        String label,
        String preview,
        String city,
        String address,
        LocationPublicStatus status
) {
}
