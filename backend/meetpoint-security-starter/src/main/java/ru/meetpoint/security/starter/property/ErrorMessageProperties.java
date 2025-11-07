package ru.meetpoint.security.starter.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exception")
public record ErrorMessageProperties(
    String passwordsDoNotMatch,
    String invalidInputs,
    String unauthorized,
    String forbidden,
    String internalError,
    String emailAlreadyExist,
    String emailAlreadyVerified,
    String userDataNotFound,
    String accountNotVerified,
    String accountBanned,
    String incorrectLoginCredentials,
    String refreshTokenInvalid,
    String emailCodeExpired,
    String emailCodeInvalid,
    String invalidJwt
) {
}
