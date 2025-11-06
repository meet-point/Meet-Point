package ru.meetpoint.authservice.data.dto.request.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.meetpoint.authservice.util.validation.annotation.MinYear;
import ru.meetpoint.authservice.util.validation.annotation.StrongPassword;

import java.time.LocalDate;

import static ru.meetpoint.authservice.config.property.ValidationConstants.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "RegistrationForm",
        description = "Form for user registration.  Containing all common fields."
)
public class RegistrationForm {

    @NotBlank(message = "First name should not be empty!")
    @Size(
            min = FIRST_NAME_MIN_LENGTH,
            max = FIRST_NAME_MAX_LENGTH,
            message = "First name must be between {min} and {max} characters."
    )
    @Schema(
            description = "User's first name.",
            example = "Ivan",
            minLength = FIRST_NAME_MIN_LENGTH,
            maxLength = FIRST_NAME_MAX_LENGTH
    )
    private String firstName;

    @Size(
            min = MIDDLE_NAME_MIN_LENGTH,
            max = MIDDLE_NAME_MAX_LENGTH,
            message = "Middle name must be between {min} and {max} characters."
    )
    @Schema(
            description = "User's middle name.",
            example = "Ivanovich",
            minLength = MIDDLE_NAME_MIN_LENGTH,
            maxLength = MIDDLE_NAME_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String middleName;

    @NotBlank(message = "Last name should not be empty!")
    @Size(
            min = LAST_NAME_MIN_LENGTH,
            max = LAST_NAME_MAX_LENGTH,
            message = "Last name must be between {min} and {max} characters."
    )
    @Schema(
            description = "User's last name.",
            example = "Ivanov",
            minLength = LAST_NAME_MIN_LENGTH,
            maxLength = LAST_NAME_MAX_LENGTH
    )
    private String lastName;

    @NotNull(message = "Birth date must not be null!")
    @Past(message = "Birth date must be in the past!")
    @MinYear(year = BIRTHDATE_MIN_YEAR)
    @Schema(
            description = "User's birth date. Must be in the past and not earlier than " + BIRTHDATE_MIN_YEAR + " year.",
            example = "1990-01-01",
            type = "string",
            format = "date"
    )
    private LocalDate birthDate;

    @NotBlank(message = "Email should not be empty!")
    @Size(
            min = EMAIL_MIN_LENGTH,
            max = EMAIL_MAX_LENGTH,
            message = "Email length must be between {min} and {max} characters!"
    )
    @Email(message = "Email must be valid!")
    @Schema(
            description = "User's email address.",
            example = "example@example.com",
            minLength = EMAIL_MIN_LENGTH,
            maxLength = EMAIL_MAX_LENGTH,
            format = "email"
    )
    private String email;

    @NotBlank(message = "Phone should not be empty!")
    @Pattern(
            regexp = PHONE_PATTERN,
            message = "Phone number must be in format +7 (XXX) XXX-XX-XX!"
    )
    @Schema(
            description = "User's phone number in Russian format.",
            example = "+7 (987) 654-32-10",
            pattern = PHONE_PATTERN
    )
    private String phoneNumber;

    @NotBlank(message = "Password should not be empty!")
    @Size(
            min = RAW_PASSWORD_MIN_LENGTH,
            max = RAW_PASSWORD_MAX_LENGTH,
            message = "Password length must be between {min} and {max} characters!"
    )
    @StrongPassword
    @Schema(
            description = "User's password. Must be strong and contain at least one uppercase, one lowercase, " +
                    "one digit, and one special character!",
            example = "P@ssw0rd",
            minLength = RAW_PASSWORD_MIN_LENGTH,
            maxLength = RAW_PASSWORD_MAX_LENGTH
    )
    private String password;

    @NotBlank(message = "Confirmation password should not be empty!")
    @Schema(
            description = "Password confirmation (must match the password field)!",
            example = "P@ssw0rd"
    )
    private String confirmPassword;

    @NotBlank(message = "City should not be empty!")
    @Size(
            min = CITY_NAME_MIN_LENGTH,
            max = CITY_NAME_MAX_LENGTH,
            message = "City name length must be between {min} and {max} characters!"
    )
    @Schema(
            description = "User's city.",
            example = "Moscow",
            minLength = CITY_NAME_MIN_LENGTH,
            maxLength = CITY_NAME_MAX_LENGTH
    )
    private String city;
}
