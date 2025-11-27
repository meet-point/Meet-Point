package ru.meetpoint.userservice.data.dto.response;

import lombok.Builder;
import ru.meetpoint.security.starter.data.enums.Role;
import ru.meetpoint.security.starter.data.enums.State;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserAccountResponse(
        UUID userId,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String avatarUrl,
        State state,
        Set<Role> roles,
        Timestamp dateOfCreation,
        Timestamp dateOfLastUpdate
) {
}
