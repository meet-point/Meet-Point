package ru.meetpoint.authservice.service;

import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;

import java.util.Map;
import java.util.UUID;

public interface UserDataService {

    UUID createUserData(RegistrationForm registrationForm);

    void verifyUserEmail(String email);

    boolean checkIsEmailNotVerified(String email);

    boolean checkIsEmailAvailable(String email);

    Map<String, Object> getJwtClaimsMapByEmail(String email);

}
