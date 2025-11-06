package ru.meetpoint.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.AuthConfigProperties;
import ru.meetpoint.authservice.service.RefreshTokenStoreService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisRefreshTokenStoreServiceImpl implements RefreshTokenStoreService {

    private final RedisTemplate<String, String> redisTemplate;

    private final AuthConfigProperties authConfigProperties;

    private static final String PREFIX = "refresh_token:";

    @Override
    public void storeRefreshToken(UUID userId, String refreshToken) {
        String key = PREFIX.concat(userId.toString());
        redisTemplate.opsForValue().set(key, refreshToken, authConfigProperties.refreshTokenExpiration());
    }

    @Override
    public String getRefreshToken(UUID userId) {
        return redisTemplate.opsForValue().get(PREFIX.concat(userId.toString()));
    }

    @Override
    public boolean validateRefreshToken(UUID userId, String refreshToken) {
        String storedToken = getRefreshToken(userId);
        return storedToken != null && storedToken.equals(refreshToken);
    }

    @Override
    public void deleteRefreshToken(UUID userId) {
        redisTemplate.delete(PREFIX.concat(userId.toString()));
    }
}
