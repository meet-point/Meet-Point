package ru.meetpoint.authservice.service;

import ru.meetpoint.security.starter.data.enums.AuthProvider;

import java.util.UUID;

public interface UserCredentialsService {

    void createUserCredentials(UUID userId, String email, String hashedPassword);

    boolean checkLoginCredentials(String email, String password);

    UUID credentialsExist(String providerKey, AuthProvider authProvider);

}
