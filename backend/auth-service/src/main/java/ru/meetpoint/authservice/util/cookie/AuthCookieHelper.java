package ru.meetpoint.authservice.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.JwtConfigProperties;
import ru.meetpoint.authservice.config.property.MailConfigProperties;
import ru.meetpoint.authservice.util.converter.EncryptConverter;

@Component
@RequiredArgsConstructor
public class AuthCookieHelper {

    private final JwtConfigProperties jwtConfigProperties;

    private final MailConfigProperties mailConfigProperties;

    private final EncryptConverter encryptConverter;

    public void setRefreshTokenCookieToHttpResponse(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie cookie = new Cookie(jwtConfigProperties.refreshTokenCookieKey(), refreshToken);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge((int) jwtConfigProperties.refreshTokenExpiration().getSeconds());

        httpServletResponse.addCookie(cookie);
    }

    public void setEmailCookieToHttpResponse(HttpServletResponse httpServletResponse, String email) {
        String encryptedEmail = encryptConverter.convertToDatabaseColumn(email);

        Cookie cookie = new Cookie(mailConfigProperties.emailCookieKey(), encryptedEmail);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge((int) mailConfigProperties.emailVerificationCodeExpiration().getSeconds());

        httpServletResponse.addCookie(cookie);
    }

    public void deleteRefreshTokenCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(jwtConfigProperties.refreshTokenCookieKey(), null);
        this.cookieDefaultSettings(cookie);
        cookie.setMaxAge(0);

        httpServletResponse.addCookie(cookie);
    }

    public void deleteEmailCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(mailConfigProperties.emailCookieKey(), null);
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
