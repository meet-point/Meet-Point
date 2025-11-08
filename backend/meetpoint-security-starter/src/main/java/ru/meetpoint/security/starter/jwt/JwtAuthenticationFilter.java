package ru.meetpoint.security.starter.jwt;

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.meetpoint.security.starter.data.enums.Role;
import ru.meetpoint.security.starter.data.enums.State;
import ru.meetpoint.security.starter.data.principal.impl.MeetPointUserDetails;
import ru.meetpoint.security.starter.property.ErrorMessageProperties;
import ru.meetpoint.security.starter.property.JwtProperties;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static ru.meetpoint.security.starter.property.SecurityEndpoints.PUBLIC_ENDPOINTS;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final AntPathMatcher antPathMatcher;

    private final JwtProperties jwtProperties;

    private final ErrorMessageProperties errorMessageProperties;

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
            throw new BadCredentialsException(errorMessageProperties.invalidJwt(), exception);
        }

        String userEmail = claims.getSubject();
        UUID userId = UUID.fromString(claims.get(jwtProperties.jwtUserIdKey(), String.class));
        Set<Role> userRoles = getRolesFromClaims(claims);

        log.warn("All claims are get!");

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.warn("If at doFilterInternal");

            MeetPointUserDetails meetPointUserDetails = new MeetPointUserDetails(userId, userEmail, null,
                    State.ACTIVE, userRoles, new HashSet<>());

            log.warn("Get meetPointUserDetails");

            MeetPointJwtAuthentication meetPointJwtAuthentication = new MeetPointJwtAuthentication(token);
            meetPointJwtAuthentication.setMeetPointUserDetails(meetPointUserDetails);
            meetPointJwtAuthentication.setAuthenticated(true);

            log.warn("Set token, meetPointUserDetails and authenticated true to meetPointJwtAuthentication");

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(meetPointJwtAuthentication);
            SecurityContextHolder.setContext(securityContext);

            log.warn("Set security context");

        }

        log.warn("Before doFilter");

        filterChain.doFilter(request, response);
    }

    public boolean isPublicRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return Arrays.asList(PUBLIC_ENDPOINTS).stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }

    private Set<Role> getRolesFromClaims(Claims claims) {
        List<?> rolesList = claims.get("roles", List.class);
        Set<Role> userRoles = new HashSet<>();

        if (rolesList != null) {
            for (Object role : rolesList) {
                if (role instanceof String) {
                    userRoles.add(Role.valueOf((String) role));
                } else if (role instanceof Role) {
                    userRoles.add((Role) role);
                }
            }
        }

        return userRoles;
    }
}
