package ru.meetpoint.eventservice.data.dto.response.event;

import lombok.Builder;
import ru.meetpoint.eventservice.data.dto.response.location.LocationShortResponse;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagementInfoResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.enums.EventAccessStatus;
import ru.meetpoint.eventservice.data.enums.EventPublishStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
public record EventDetailedResponse(
        UUID eventId,
        String label,
        String preview,
        String description,
        EventAccessStatus accessStatus,
        EventPublishStatus publishStatus,
        Set<String> categories,
        Timestamp dateTime,
        int maxAllowedPeople,
        int registeredNow,
        BigDecimal cost,
        Timestamp publishedAt,
        LocationShortResponse location,
        OrganizationShortResponse organization,
        ManagementInfoResponse managementInfo
) {
}
