package ru.meetpoint.authservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.data.principal.impl.MeetPointUserDetails;
import ru.meetpoint.authservice.service.impl.MeetPointUserDetailsService;

import java.io.IOException;
import java.util.stream.Stream;

import static ru.meetpoint.authservice.config.security.SecurityEndpoints.PUBLIC_ENDPOINTS;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final ErrorMessageProperties errorMessageProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final MeetPointUserDetailsService meetPointUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        boolean isPublic = isPublicRequest(request);
        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        Claims claims;
        try {
            claims = jwtProvider.extractAllClaims(token);
        } catch (Exception exception) {
            throw new BadCredentialsException(errorMessageProperties.getInvalidJwt(), exception);
        }

        String email = claims.getSubject();
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            MeetPointUserDetails meetPointUserDetails = meetPointUserDetailsService.loadUserByUsername(email);

            if (jwtProvider.isTokenValid(token)) {
                MeetPointJwtAuthentication meetPointJwtAuthentication = new MeetPointJwtAuthentication(token);
                meetPointJwtAuthentication.setMeetPointUserDetails(meetPointUserDetails);
                meetPointJwtAuthentication.setAuthenticated(true);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(meetPointJwtAuthentication);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        filterChain.doFilter(request, response);
    }

    public boolean isPublicRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return Stream.concat(
                        Arrays.asList(PUBLIC_ENDPOINTS).stream(),
                        HttpMethod.GET.matches(request.getMethod())
                                ? Arrays.asList(PUBLIC_ENDPOINTS).stream()
                                : Stream.empty()
                )
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
