package ru.meetpoint.authservice.config.property;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "encryption")
public record EncryptionConfigProperties(
        String privateKey,
        String publicKey
) {
}
