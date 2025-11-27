package ru.meetpoint.userservice.data.dto.response;

import lombok.Builder;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
public record ProfileResponse(
        String firstName,
        String middleName,
        String lastName,
        Date dateOfBirth,
        String email,
        String phoneNumber,
        String city,
        String avatarUrl,
        String aboutMe,
        Timestamp dateOfCreation
) {
}
