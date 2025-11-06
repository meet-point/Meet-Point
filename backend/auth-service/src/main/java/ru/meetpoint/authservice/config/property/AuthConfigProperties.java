package ru.meetpoint.authservice.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@ConfigurationProperties(prefix = "auth")
public record AuthConfigProperties(
        String privateKey,
        String publicKey,
        Duration accessTokenExpiration,
        Duration refreshTokenExpiration,
        String refreshTokenCookieKey,
        String emailCookieKey,
        int emailVerificationCodeLength,
        Duration emailVerificationCodeExpiration,
        String jwtSubjectKey,
        String jwtUserIdKey,
        String jwtRolesKey,
        String jwtNameKey
) {
}
