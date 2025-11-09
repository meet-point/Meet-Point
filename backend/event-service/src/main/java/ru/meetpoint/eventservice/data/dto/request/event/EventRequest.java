package ru.meetpoint.eventservice.data.dto.request.event;

import lombok.Builder;
import ru.meetpoint.eventservice.data.enums.EventAccessStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
public record EventRequest(
        String label,
        String preview,
        String description,
        EventAccessStatus accessStatus,
        Set<String> categories,
        Timestamp dateTime,
        int maxAllowedPeople,
        BigDecimal cost,
        Timestamp publishTime,
        UUID locationId,
        UUID organizationId
) {
}
