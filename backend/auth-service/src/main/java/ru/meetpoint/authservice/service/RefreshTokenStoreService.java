package ru.meetpoint.authservice.service;

import java.util.UUID;

public interface RefreshTokenStoreService {

    void storeRefreshToken(UUID userId, String refreshToken);

    String getRefreshToken(UUID userId);

    void deleteRefreshToken(UUID userId);

    boolean validateRefreshToken(UUID userId, String refreshToken);
}
