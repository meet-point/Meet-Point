package ru.meetpoint.userservice.data.dto.request;

import lombok.Builder;
import ru.meetpoint.userservice.data.entity.enums.ComplaintStatus;

import java.util.UUID;

@Builder
public record CreateUserComplaintRequest(
        UUID userId,
        UUID authorId,
        String shortReason,
        String description
) {
}
