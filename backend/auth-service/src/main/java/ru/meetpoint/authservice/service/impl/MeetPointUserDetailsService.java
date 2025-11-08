package ru.meetpoint.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.meetpoint.authservice.data.entity.UserCredentials;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.repository.UserCredentialsRepository;
import ru.meetpoint.authservice.repository.UserDataRepository;
import ru.meetpoint.security.starter.data.enums.AuthProvider;
import ru.meetpoint.security.starter.data.principal.impl.MeetPointUserDetails;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetPointUserDetailsService implements UserDetailsService {

    private final UserDataRepository userDataRepository;

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public MeetPointUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email %s not found!".formatted(username)));

        List<UserCredentials> userCredentials = userCredentialsRepository.findByUserData(userData);
        Set<AuthProvider> authProviders = userCredentials.stream()
                .map(UserCredentials::getProviderType)
                .collect(Collectors.toSet());

        String hashedPassword = String.valueOf(userCredentials.stream()
                .map(UserCredentials::getHashPassword)
                .filter(Objects::nonNull)
                .findFirst());

        return new MeetPointUserDetails(userData.getUserId(), userData.getEmail(), hashedPassword, userData.getState(),
                userData.getRoles(), authProviders);
    }
}
