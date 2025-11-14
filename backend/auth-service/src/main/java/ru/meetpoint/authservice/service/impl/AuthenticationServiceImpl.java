package ru.meetpoint.authservice.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.JwtConfigProperties;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.config.property.MailConfigProperties;
import ru.meetpoint.authservice.data.dto.inner.JwtTokenResponse;
import ru.meetpoint.authservice.data.dto.request.form.LoginForm;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.dto.response.AccessTokenResponse;
import ru.meetpoint.authservice.error.exception.BadRequestException;
import ru.meetpoint.authservice.error.exception.ForbiddenException;
import ru.meetpoint.authservice.error.exception.UnauthorizedException;
import ru.meetpoint.authservice.service.*;
import ru.meetpoint.authservice.util.mail.MailService;
import ru.meetpoint.authservice.util.validation.validator.RegistrationFormValidator;
import ru.meetpoint.security.starter.data.enums.ErrorCode;
import ru.meetpoint.security.starter.jwt.JwtProvider;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDataService userDataService;

    private final UserCredentialsService userCredentialsService;

    private final EmailVerificationCodeStoreService emailVerificationCodeStoreService;

    private final MailService mailService;

    private final RefreshTokenStoreService refreshTokenStoreService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RegistrationFormValidator registrationFormValidator;

    private final JwtConfigProperties jwtConfigProperties;

    private final MailConfigProperties mailConfigProperties;

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public UUID register(RegistrationForm registrationForm) {
        registrationFormValidator.validateRegistrationForm(registrationForm);

        String email = registrationForm.getEmail();
        if (!checkEmailIsAvailable(email)) {
            throw new BadRequestException(ErrorCode.EMAIL_ALREADY_EXIST, errorMessageProperties.getEmailAlreadyExist());
        }

        UUID userId = userDataService.createUserData(registrationForm);
        String hashedPassword = passwordEncoder.encode(registrationForm.getPassword());
        userCredentialsService.createUserCredentials(userId, email, hashedPassword);
        sendAndStoreEmailVerificationCode(email);

        System.out.println("end register");
        return userId;
    }

    @Override
    public JwtTokenResponse login(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        if (userCredentialsService.checkLoginCredentials(email, password)) {
            Map<String, Object> jwtClaims = userDataService.getJwtClaimsMapByEmail(email);

            System.out.println(jwtClaims.get(jwtConfigProperties.jwtUserIdKey()));
            System.out.println(jwtClaims.get(jwtConfigProperties.jwtNameKey()));
            System.out.println(jwtClaims.get(jwtConfigProperties.jwtRolesKey()));
            return generateTokens(email, jwtClaims);
        } else {
            throw new BadRequestException(ErrorCode.INCORRECT_LOGIN_CREDENTIALS,
                    errorMessageProperties.getIncorrectLoginCredentials());
        }
    }

    @Override
    public void logout(UUID userId) {
        refreshTokenStoreService.deleteRefreshToken(userId);
    }

    @Override
    public void verify(String decryptedEmail, String verificationCode) {
        if (userDataService.checkIsEmailNotVerified(decryptedEmail)) {
            if (emailVerificationCodeStoreService.getEmailConfirmationCode(decryptedEmail) == null) {
                sendAndStoreEmailVerificationCode(decryptedEmail);
                throw new BadRequestException(ErrorCode.EMAIL_CODE_EXPIRED,
                        errorMessageProperties.getEmailCodeExpired());
            }

            if (emailVerificationCodeStoreService.validateEmailConfirmationCode(decryptedEmail, verificationCode)) {
                userDataService.verifyUserEmail(decryptedEmail);
                emailVerificationCodeStoreService.deleteEmailConfirmationCode(decryptedEmail);

            } else {
                throw new BadRequestException(ErrorCode.EMAIL_CODE_INVALID,
                        errorMessageProperties.getEmailCodeInvalid());
            }
        } else {
            throw new ForbiddenException(ErrorCode.EMAIL_ALREADY_VERIFIED,
                    errorMessageProperties.getEmailAlreadyVerified());
        }
    }

    @Override
    public AccessTokenResponse refresh(String refreshToken) {
        String email = jwtProvider.extractSubject(refreshToken);
        Map<String, Object> jwtClaims = userDataService.getJwtClaimsMapByEmail(email);
        UUID userId = (UUID) jwtClaims.get("userId");

        if (refreshTokenStoreService.validateRefreshToken(userId, refreshToken)) {
            return new AccessTokenResponse(jwtProvider.generateAccessToken(email, jwtClaims));
        }

        throw new UnauthorizedException(ErrorCode.REFRESH_TOKEN_INVALID,
                errorMessageProperties.getRefreshTokenInvalid());
    }

    @Override
    public boolean checkEmailIsAvailable(String email) {
        return userDataService.checkIsEmailAvailable(email);
    }


    private void sendAndStoreEmailVerificationCode(String email) {
        String verificationCode = generateEmailVerificationCode();
        emailVerificationCodeStoreService.storeEmailVerificationCode(email, verificationCode);
        mailService.sendVerificationCodeToEmail(email, verificationCode);
    }

    private String generateEmailVerificationCode() {
        int length = mailConfigProperties.emailVerificationCodeLength();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;

        return String.valueOf(min + new SecureRandom().nextInt(max - min + 1));
    }

    private JwtTokenResponse generateTokens(String email, Map<String, Object> claims) {
        UUID userId = (UUID) claims.get(jwtConfigProperties.jwtUserIdKey());
        String refreshToken = jwtProvider.generateRefreshToken(email);
        String accessToken = jwtProvider.generateAccessToken(email, claims);

        System.out.println("AuthServiceImpl generateTokens check");
        Claims checkedClaims = jwtProvider.extractAllClaims(accessToken);
        System.out.println(checkedClaims.getSubject());
        System.out.println(checkedClaims.get(jwtConfigProperties.jwtUserIdKey()));
        System.out.println(checkedClaims.get(jwtConfigProperties.jwtNameKey()));
        System.out.println(checkedClaims.get(jwtConfigProperties.jwtRolesKey()));

        refreshTokenStoreService.storeRefreshToken(userId, refreshToken);
        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
