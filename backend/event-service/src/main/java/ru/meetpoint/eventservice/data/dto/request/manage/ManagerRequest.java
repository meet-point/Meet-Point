package ru.meetpoint.eventservice.data.dto.request.manage;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ManagerRequest(
        UUID managerId,
        String email
) {
}
