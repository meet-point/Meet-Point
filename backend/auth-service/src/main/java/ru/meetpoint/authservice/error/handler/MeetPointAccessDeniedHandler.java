package ru.meetpoint.authservice.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.error.dto.ApiErrorResponse;
import ru.meetpoint.authservice.error.enums.ErrorCode;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MeetPointAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(403)
                .errorCode(ErrorCode.FORBIDDEN)
                .exceptionMessage(errorMessageProperties.getForbidden())
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println(objectMapper.writeValueAsString(apiErrorResponse));
    }
}
