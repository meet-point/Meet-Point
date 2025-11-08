package ru.meetpoint.authservice.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfigProperties(
        String privateKey,
        String publicKey,
        Duration accessTokenExpiration,
        Duration refreshTokenExpiration,
        String refreshTokenCookieKey,
        String jwtSubjectKey,
        String jwtUserIdKey,
        String jwtRolesKey,
        String jwtNameKey
) {
}
