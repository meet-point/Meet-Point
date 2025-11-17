package ru.meetpoint.eventservice.data.dto.response.manage;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
public record ManagementInfoResponse(
        UUID creatorId,
        Timestamp createdAt,
        UUID lastUpdateBy,
        Timestamp updatedAt
) {
}
