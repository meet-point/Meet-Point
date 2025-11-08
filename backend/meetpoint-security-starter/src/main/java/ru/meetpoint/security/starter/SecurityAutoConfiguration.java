package ru.meetpoint.security.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.meetpoint.security.starter.config.MeetPointSecurityConfig;
import ru.meetpoint.security.starter.property.ErrorMessageProperties;
import ru.meetpoint.security.starter.property.JwtProperties;

@AutoConfiguration
@Import(MeetPointSecurityConfig.class)
public class SecurityAutoConfiguration {}
