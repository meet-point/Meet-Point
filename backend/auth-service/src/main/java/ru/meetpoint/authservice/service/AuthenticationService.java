package ru.meetpoint.authservice.service;

import ru.meetpoint.authservice.data.dto.inner.JwtTokenResponse;
import ru.meetpoint.authservice.data.dto.request.form.LoginForm;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.dto.response.AccessTokenResponse;

import java.util.UUID;

public interface AuthenticationService {

    UUID register(RegistrationForm registrationForm);

    JwtTokenResponse login(LoginForm loginForm);

    void logout(UUID userId);

    void verify(String decryptedEmail, String verificationCode);

    AccessTokenResponse refresh(String refreshToken);

    boolean checkEmailIsAvailable(String email);
}
