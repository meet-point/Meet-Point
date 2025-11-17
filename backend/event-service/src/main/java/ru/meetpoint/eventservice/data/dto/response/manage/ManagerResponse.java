package ru.meetpoint.eventservice.data.dto.response.manage;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ManagerResponse(
        UUID managerId,
        String email
) {
}
