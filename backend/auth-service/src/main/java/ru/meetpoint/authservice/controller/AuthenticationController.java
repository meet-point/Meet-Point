package ru.meetpoint.authservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.meetpoint.authservice.api.AuthenticationApi;
import ru.meetpoint.authservice.data.dto.inner.JwtTokenResponse;
import ru.meetpoint.authservice.data.dto.request.form.LoginForm;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.dto.response.AccessTokenResponse;
import ru.meetpoint.authservice.data.dto.response.EmailAvailabilityResponse;
import ru.meetpoint.authservice.data.dto.response.RegistrationResponse;
import ru.meetpoint.authservice.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.authservice.error.dto.OperationResponse;
import ru.meetpoint.authservice.error.enums.ErrorCode;
import ru.meetpoint.authservice.error.exception.ForbiddenException;
import ru.meetpoint.authservice.error.exception.UnauthorizedException;
import ru.meetpoint.authservice.service.AuthenticationService;
import ru.meetpoint.authservice.util.cookie.AuthCookieHelper;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    private final AuthCookieHelper authCookieHelper;

    @Override
    public RegistrationResponse register(RegistrationForm registrationForm, HttpServletResponse httpServletResponse) {
        UUID userId = authenticationService.register(registrationForm);
        authCookieHelper.setEmailCookieToHttpResponse(httpServletResponse, registrationForm.getEmail());
        return RegistrationResponse.builder()
                .userId(userId)
                .isSuccess(true)
                .build();
    }

    @Override
    public AccessTokenResponse login(LoginForm loginForm, HttpServletResponse httpServletResponse) {
        try {
            JwtTokenResponse jwtTokenResponse = authenticationService.login(loginForm);
            authCookieHelper.setRefreshTokenCookieToHttpResponse(httpServletResponse, jwtTokenResponse.refreshToken());
            return AccessTokenResponse.builder()
                    .accessToken(jwtTokenResponse.accessToken())
                    .build();
        } catch (ForbiddenException forbiddenException) {
            if (forbiddenException.getErrorCode() == ErrorCode.ACCOUNT_NOT_VERIFIED) {
                authCookieHelper.setEmailCookieToHttpResponse(httpServletResponse, loginForm.getEmail());
            }

            throw forbiddenException;
        }
    }

    @Override
    public OperationResponse logout(UnifiedAuthPrincipal authPrincipal, HttpServletResponse httpServletResponse) {
        System.out.println(authPrincipal.getUserId());

        authenticationService.logout(authPrincipal.getUserId());

        authCookieHelper.deleteRefreshTokenCookie(httpServletResponse);
        authCookieHelper.deleteEmailCookie(httpServletResponse);
        authCookieHelper.deleteJsessionIdCookie(httpServletResponse);

        return OperationResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public OperationResponse verify(String encryptedEmail, String verificationCode) {
        String decryptedEmail = authCookieHelper.decryptEmailCookie(encryptedEmail);
        authenticationService.verify(decryptedEmail, verificationCode);

        return OperationResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public AccessTokenResponse refreshAccessToken(String refreshToken, HttpServletResponse httpServletResponse) {
        try {
            return authenticationService.refresh(refreshToken);
        } catch (UnauthorizedException unauthorizedException) {
            authCookieHelper.deleteRefreshTokenCookie(httpServletResponse);
            authCookieHelper.deleteEmailCookie(httpServletResponse);
            authCookieHelper.deleteJsessionIdCookie(httpServletResponse);

            throw unauthorizedException;
        }
    }

    @Override
    public EmailAvailabilityResponse checkEmailIsAvailable(String email) {
        return EmailAvailabilityResponse.builder()
                .isAvailable(authenticationService.checkEmailIsAvailable(email))
                .build();
    }
}
