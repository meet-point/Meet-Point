package ru.meetpoint.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.meetpoint.authservice.config.property.JwtConfigProperties;
import ru.meetpoint.authservice.config.property.EncryptionConfigProperties;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.config.property.MailConfigProperties;
import ru.meetpoint.authservice.util.logging.aspect.MethodLoggingAspect;

@SpringBootApplication
@EnableConfigurationProperties({
        JwtConfigProperties.class,
        EncryptionConfigProperties.class,
        ErrorMessageProperties.class,
        MailConfigProperties.class
})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
