package ru.meetpoint.security.starter.property;

public class SecurityEndpoints {

    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/check_email",
            "/api/v1/auth/refresh",
            "/api/v1/events/**",
            "/api/v1/locations/**",
            "/api/v1/organizations/**",
            "/api/v1/topics/**",
            "/actuator/**",
            "/error/**"
    };

    public static final String[] ANONYMOUS_ENDPOINTS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/verify/**",
    };

    public static final String[] AUTHENTICATED_ENDPOINTS = {
            "/api/v1/auth/logout",
            "/api/v1/events/**",
            "/api/v1/locations/**",
            "/api/v1/organizations/**",
            "/api/v1/topics/**",
            "/api/v1/admin/**",
            "/api/v1/profile/**",
            "/api/v1/photos/avatars/**",
            "/api/v1/form/**",
            "/api/v1/friends/**",
            "/api/v1/notifications/**",
    };

    public static final String[] SWAGGER_ENDPOINTS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    public static final String[] ALL_OPTIONS = {
            "/**"
    };

    public static final String CSRF_PROTECTED_ENDPOINT = "/api/v1/auth/refresh";

}
