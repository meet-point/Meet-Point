package ru.meetpoint.authservice.data.dto.request.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginForm", description = "Form for user login")
public class LoginForm {

    @NotBlank(message = "Email should not be empty!")
    @Email(message = "Email must be valid!")
    @Schema(description = "User's email.", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password should not be empty!")
    @Schema(description = "User's password.", example = "P@ssw0rd.")
    private String password;
}
