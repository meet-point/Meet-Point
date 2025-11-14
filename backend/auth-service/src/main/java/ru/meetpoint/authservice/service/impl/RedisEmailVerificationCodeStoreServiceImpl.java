package ru.meetpoint.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.MailConfigProperties;
import ru.meetpoint.authservice.service.EmailVerificationCodeStoreService;

@Service
@RequiredArgsConstructor
public class RedisEmailVerificationCodeStoreServiceImpl implements EmailVerificationCodeStoreService {

    private final RedisTemplate<String, String> redisTemplate;

    private final MailConfigProperties mailConfigProperties;

    private static final String PREFIX = "verify_code:";

    @Override
    public void storeEmailVerificationCode(String email, String verificationCode) {
        String key = PREFIX.concat(email);
        redisTemplate.opsForValue().set(key, verificationCode.trim(),
                mailConfigProperties.emailVerificationCodeExpiration().getSeconds());
    }

    @Override
    public String getEmailConfirmationCode(String email) {
        String key = PREFIX.concat(email);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean validateEmailConfirmationCode(String email, String code) {
        String storedCode = getEmailConfirmationCode(email);
        return storedCode.trim().equals(code.trim());
    }

    @Override
    public void deleteEmailConfirmationCode(String email) {
        redisTemplate.delete(PREFIX.concat(email));
    }
}
