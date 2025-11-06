package ru.meetpoint.authservice.util.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.meetpoint.authservice.util.validation.annotation.StrongPassword;

import static ru.meetpoint.authservice.config.property.ValidationConstants.*;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        if (password == null || password.isBlank()) {
            return false;
        }

        if (password.length() < RAW_PASSWORD_MIN_LENGTH) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain at least 8 characters long!").addConstraintViolation();
            return false;
        }

        if (!password.matches("^[a-zA-Z0-9.,<>?!/|@#$%^&*()_+]+$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain only numbers, Latin uppercase or lowercase letters and special characters!"
            ).addConstraintViolation();
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain at least one uppercase Latin letter!"
            ).addConstraintViolation();
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain at least one lowercase Latin letter!"
            ).addConstraintViolation();
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain at least one digit!"
            ).addConstraintViolation();
            return false;
        }

        if (!password.matches(".*[.,<>?!/|@#$%^&*()_+].*")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password must contain at least one special character (.,<>?!/|@#$%^&*()_+)!"
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
