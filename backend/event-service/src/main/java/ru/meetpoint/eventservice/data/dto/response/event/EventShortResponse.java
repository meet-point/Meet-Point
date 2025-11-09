package ru.meetpoint.eventservice.data.dto.response.event;

import lombok.Builder;
import ru.meetpoint.eventservice.data.enums.EventAccessStatus;
import ru.meetpoint.eventservice.data.enums.EventPublishStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
public record EventShortResponse(
        UUID eventId,
        String label,
        String preview,
        EventAccessStatus accessStatus,
        EventPublishStatus publishStatus,
        Set<String> categories,
        Timestamp dateTime,
        int maxAllowedPeople,
        int registeredNow,
        BigDecimal cost
) {
}
