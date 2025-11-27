package ru.meetpoint.userservice.data.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserAccountRequest(
        UUID userId,
        String firstName,
        String middleName,
        String lastName,
        String dateOfBirth,
        String email,
        String phoneNumber,
        String city
) {
}
