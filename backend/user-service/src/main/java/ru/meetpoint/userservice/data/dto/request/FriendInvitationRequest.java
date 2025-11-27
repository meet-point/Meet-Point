package ru.meetpoint.userservice.data.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FriendInvitationRequest(
        UUID userId,
        UUID friendId,
        String shortMessage
) {
}
