package ru.meetpoint.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.data.entity.UserCredentials;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.error.exception.BadRequestException;
import ru.meetpoint.authservice.error.exception.ForbiddenException;
import ru.meetpoint.authservice.mapper.UserCredentialsMapper;
import ru.meetpoint.authservice.repository.UserCredentialsRepository;
import ru.meetpoint.authservice.repository.UserDataRepository;
import ru.meetpoint.authservice.service.UserCredentialsService;
import ru.meetpoint.security.starter.data.enums.AuthProvider;
import ru.meetpoint.security.starter.data.enums.ErrorCode;
import ru.meetpoint.security.starter.data.enums.State;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserDataRepository userDataRepository;

    private final UserCredentialsMapper userCredentialsMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public void createUserCredentials(UUID userId, String email, String hashedPassword) {
        UserData userData = userDataRepository
                .findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_DATA_NOT_FOUND,
                        errorMessageProperties.getUserDataNotFound()));

        UserCredentials userCredentials = userCredentialsMapper.toUserCredentials(userData, email, hashedPassword);
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public boolean checkLoginCredentials(String email, String password) {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findActiveByProviderKeyAndProviderType(
                email, AuthProvider.LOCAL);

        if (userCredentials.isPresent()) {
            System.out.println(bCryptPasswordEncoder.encode(password));
            String hashedPassword = userCredentials.get().getHashPassword();
            UserData userData = userCredentials.get().getUserData();

            if (userData.getState() == State.NOT_VERIFIED) {
                throw new ForbiddenException(ErrorCode.ACCOUNT_NOT_VERIFIED,
                        errorMessageProperties.getAccountNotVerified());
            }

            if (userData.getState() == State.BANNED) {
                throw new ForbiddenException(ErrorCode.ACCOUNT_BANNED, errorMessageProperties.getAccountBanned());
            }

            return bCryptPasswordEncoder.matches(password, hashedPassword);
        }
        return false;
    }

    @Override
    public UUID credentialsExist(String providerKey, AuthProvider authProvider) {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findActiveByProviderKeyAndProviderType(
                providerKey, authProvider);
        return userCredentials.map(credentials -> credentials.getUserData().getUserId()).orElse(null);
    }
}
