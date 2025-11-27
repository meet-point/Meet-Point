package ru.meetpoint.userservice.data.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FriendShortResponse(
        UUID userId,
        String firstName,
        String middleName,
        String lastName,
        String avatarUrl
) {
}
