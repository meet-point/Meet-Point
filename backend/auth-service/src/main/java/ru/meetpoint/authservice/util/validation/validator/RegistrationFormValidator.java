package ru.meetpoint.authservice.util.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.config.property.AuthConfigProperties;
import ru.meetpoint.authservice.config.property.ErrorMessageProperties;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.error.exception.BadRequestException;
import ru.meetpoint.authservice.error.enums.ErrorCode;

@Component
@RequiredArgsConstructor
public class RegistrationFormValidator {

    private final AuthConfigProperties authConfigProperties;

    private final ErrorMessageProperties errorMessageProperties;

    public void validateRegistrationForm(RegistrationForm registrationForm) {
        String password = registrationForm.getPassword();
        String confirmPassword = registrationForm.getConfirmPassword();

        if (password == null || !password.equals(confirmPassword)) {
            throw new BadRequestException(ErrorCode.PASSWORD_DO_NOT_MATCH, errorMessageProperties.getPasswordsDoNotMatch());
        }
    }
}
