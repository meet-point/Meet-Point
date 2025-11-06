package ru.meetpoint.authservice.mapper;

import org.springframework.stereotype.Component;
import ru.meetpoint.authservice.data.dto.request.form.RegistrationForm;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.data.enums.Role;
import ru.meetpoint.authservice.data.enums.State;

import java.sql.Timestamp;
import java.util.Set;

@Component
public class UserDataMapper {

    public UserData toUserData(RegistrationForm registrationForm) {
        return UserData.builder()
                .firstName(registrationForm.getFirstName())
                .middleName(registrationForm.getMiddleName())
                .lastName(registrationForm.getLastName())
                .email(registrationForm.getEmail())
                .phoneNumber(registrationForm.getPhoneNumber())
                .state(State.NOT_VERIFIED)
                .roles(Set.of(Role.USER))
                .build();
    }
}
