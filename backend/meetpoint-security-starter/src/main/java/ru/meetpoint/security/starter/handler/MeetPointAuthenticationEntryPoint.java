package ru.meetpoint.security.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.meetpoint.security.starter.data.enums.ErrorCode;
import ru.meetpoint.security.starter.property.ErrorMessageProperties;
import ru.meetpoint.security.starter.response.ApiErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeetPointAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.warn("Authentication exception: code={}, message={}. Request parameters: cookies={}, requestURI={}, method={}, request={}",
                ErrorCode.UNAUTHORIZED, authException.getMessage(), Arrays.toString(request.getCookies()),
                request.getRequestURI(), request.getMethod(), request);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(401)
                .errorCode(ErrorCode.UNAUTHORIZED)
                .exceptionMessage(errorMessageProperties.unauthorized())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(apiErrorResponse);

        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
    }
}
