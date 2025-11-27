package ru.meetpoint.userservice.data.dto.response;

import lombok.Builder;
import ru.meetpoint.userservice.data.entity.enums.ComplaintStatus;

import java.util.UUID;

@Builder
public record UserComplaintShortResponse(
        UUID complaintId,
        UUID userId,
        String userFullName,
        UUID authorId,
        String authorFullName,
        ComplaintStatus status,
        String shortReason,
        String description
) {
}
