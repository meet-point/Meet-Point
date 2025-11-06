package ru.meetpoint.authservice.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.AuthConfigProperties;
import ru.meetpoint.authservice.util.converter.EncryptConverter;

@Component
@RequiredArgsConstructor
public class AuthCookieHelper {

    private final AuthConfigProperties authConfigProperties;

    private final EncryptConverter encryptConverter;

    public void setRefreshTokenCookieToHttpResponse(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie cookie = new Cookie(authConfigProperties.refreshTokenCookieKey(), refreshToken);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge((int) authConfigProperties.refreshTokenExpiration().getSeconds());

        httpServletResponse.addCookie(cookie);
    }

    public void setEmailCookieToHttpResponse(HttpServletResponse httpServletResponse, String email) {
        String encryptedEmail = encryptConverter.convertToDatabaseColumn(email);

        Cookie cookie = new Cookie(authConfigProperties.emailCookieKey(), encryptedEmail);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge((int) authConfigProperties.emailVerificationCodeExpiration().getSeconds());

        httpServletResponse.addCookie(cookie);
    }

    public void deleteRefreshTokenCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(authConfigProperties.refreshTokenCookieKey(), null);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge(0);

        httpServletResponse.addCookie(cookie);
    }

    public void deleteEmailCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(authConfigProperties.emailCookieKey(), null);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge(0);

        httpServletResponse.addCookie(cookie);
    }

    public void deleteJsessionIdCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge(0);

        httpServletResponse.addCookie(cookie);
    }

    public String decryptEmailCookie(String encryptedEmail){
        return encryptConverter.convertToEntityAttribute(encryptedEmail);
    }

    private void cookieDefaultSettings(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax");
    }
}
