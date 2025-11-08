package ru.meetpoint.authservice.mapper;

import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.data.entity.UserCredentials;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.security.starter.data.enums.AuthProvider;

@Component
public class UserCredentialsMapper {

    public UserCredentials toUserCredentials(UserData userData, String providerKey, String hashedPassword) {
        return UserCredentials.builder()
                .userData(userData)
                .providerType(AuthProvider.LOCAL)
                .providerKey(providerKey)
                .hashPassword(hashedPassword)
                .build();
    }
}
