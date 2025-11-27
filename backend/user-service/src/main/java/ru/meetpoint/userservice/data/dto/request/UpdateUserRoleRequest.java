package ru.meetpoint.userservice.data.dto.request;

import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.Role;

import java.util.UUID;

@Builder
public record UpdateUserRoleRequest(
        UUID userId,
        Role role,
        boolean isAssigned
) {
}
