package ru.meetpoint.authservice.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.error.dto.ApiErrorResponse;
import ru.meetpoint.authservice.error.enums.ErrorCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class MeetPointAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(401)
                .errorCode(ErrorCode.UNAUTHORIZED)
                .exceptionMessage(errorMessageProperties.getUnauthorized())
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = objectMapper.writeValueAsString(apiErrorResponse);

        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
    }
}
