package ru.meetpoint.userservice.data.dto.response;

import lombok.Builder;

@Builder
public record FriendInvitationResponse(
        FriendShortResponse shortResponse,
        String shortMessage
) {
}
