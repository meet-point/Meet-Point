package ru.meetpoint.authservice.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "mail")
public record MailConfigProperties(
        String senderUsername,
        String verificationCodeSubject,
        String verificationCodeText,
        String emailCookieKey,
        int emailVerificationCodeLength,
        Duration emailVerificationCodeExpiration
) {
}
