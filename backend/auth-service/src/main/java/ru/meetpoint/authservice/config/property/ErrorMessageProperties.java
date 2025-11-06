package ru.meetpoint.authservice.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "exception")
public class ErrorMessageProperties {
        private String passwordsDoNotMatch;
        private String invalidInputs;
        private String unauthorized;
        private String forbidden;
        private String internalError;
        private String emailAlreadyExist;
        private String emailAlreadyVerified;
        private String userDataNotFound;
        private String accountNotVerified;
        private String accountBanned;
        private String incorrectLoginCredentials;
        private String refreshTokenInvalid;
        private String emailCodeExpired;
        private String emailCodeInvalid;
        private String invalidJwt;
}
