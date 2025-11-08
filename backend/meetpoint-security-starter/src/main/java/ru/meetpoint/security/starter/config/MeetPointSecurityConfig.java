package ru.meetpoint.security.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import ru.meetpoint.security.starter.handler.MeetPointAccessDeniedHandler;
import ru.meetpoint.security.starter.handler.MeetPointAuthenticationEntryPoint;
import ru.meetpoint.security.starter.jwt.JwtAuthenticationFilter;
import ru.meetpoint.security.starter.jwt.JwtProvider;
import ru.meetpoint.security.starter.property.ErrorMessageProperties;
import ru.meetpoint.security.starter.property.JwtProperties;

@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties({
        ErrorMessageProperties.class,
        JwtProperties.class
})
public class MeetPointSecurityConfig {

    @Bean
    @ConditionalOnMissingBean
    public JwtProvider jwtProvider(JwtProperties jwtProperties) {
        return new JwtProvider(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtProvider jwtProvider,
            AntPathMatcher antPathMatcher,
            JwtProperties jwtProperties,
            ErrorMessageProperties errorMessageProperties
    ) {
        return new JwtAuthenticationFilter(jwtProvider, antPathMatcher, jwtProperties, errorMessageProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MeetPointAuthenticationEntryPoint meetPointAuthenticationEntryPoint(ErrorMessageProperties errorMessageProperties) {
        return new MeetPointAuthenticationEntryPoint(errorMessageProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MeetPointAccessDeniedHandler meetPointAccessDeniedHandler(ErrorMessageProperties errorMessageProperties) {
        return new MeetPointAccessDeniedHandler(errorMessageProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            MeetPointAccessDeniedHandler meetPointAccessDeniedHandler,
            MeetPointAuthenticationEntryPoint meetPointAuthenticationEntryPoint
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(meetPointAuthenticationEntryPoint)
                        .accessDeniedHandler(meetPointAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
