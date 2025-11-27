package ru.meetpoint.userservice.data.dto.request;

import lombok.Builder;

@Builder
public record UpdatePasswordRequest(
        String password,
        String confirmPassword
) {
}
