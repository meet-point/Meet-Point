package ru.meetpoint.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.config.property.AuthConfigProperties;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.data.enums.State;
import ru.meetpoint.authservice.error.enums.ErrorCode;
import ru.meetpoint.authservice.error.exception.BadRequestException;
import ru.meetpoint.authservice.error.exception.ForbiddenException;
import ru.meetpoint.authservice.mapper.UserDataMapper;
import ru.meetpoint.authservice.repository.UserDataRepository;
import ru.meetpoint.authservice.service.UserDataService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private final UserDataRepository userDataRepository;

    private final UserDataMapper userDataMapper;

    private final AuthConfigProperties authConfigProperties;

    private final ErrorMessageProperties errorMessageProperties;

    @Override
    public UUID createUserData(RegistrationForm registrationForm) {
        UserData userData = userDataMapper.toUserData(registrationForm);
        Timestamp now = Timestamp.from(Instant.now());

        userData.setDateOfCreation(now);
        userData.setDateOfLastUpdate(now);
        return userDataRepository.save(userData).getUserId();
    }

    @Override
    public void verifyUserEmail(String email) {
        UserData userData = userDataRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_DATA_NOT_FOUND,
                        errorMessageProperties.getUserDataNotFound()));

        if (userData.getState() != State.NOT_VERIFIED) {
            throw new ForbiddenException(ErrorCode.EMAIL_ALREADY_VERIFIED,
                    errorMessageProperties.getEmailAlreadyVerified());
        }
        userData.setState(State.ACTIVE);
        userDataRepository.save(userData);
    }

    @Override
    public boolean checkIsEmailNotVerified(String email) {
        UserData userData = userDataRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_DATA_NOT_FOUND,
                        errorMessageProperties.getUserDataNotFound()));
        return userData.getState() == State.NOT_VERIFIED;
    }

    @Override
    public boolean checkIsEmailAvailable(String email) {
        boolean flag = !userDataRepository.existsByEmail(email);
        return flag;
    }

    @Override
    public Map<String, Object> getJwtClaimsMapByEmail(String email) {
        UserData userData = userDataRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_DATA_NOT_FOUND,
                        errorMessageProperties.getUserDataNotFound()));

        String fullName = String.join(" ",
                userData.getFirstName(),
                Optional.ofNullable(userData.getMiddleName()).orElse(""),
                userData.getLastName()
        ).replaceAll("\\s+", " ").trim();

        return Map.of(
                authConfigProperties.jwtUserIdKey(), userData.getUserId(),
                authConfigProperties.jwtRolesKey(), userData.getRoles(),
                authConfigProperties.jwtNameKey(), fullName
        );
    }
}
