package ru.meetpoint.authservice.data.principal;

import ru.meetpoint.authservice.data.enums.AuthProvider;
import ru.meetpoint.authservice.data.enums.Role;
import ru.meetpoint.authservice.data.enums.State;

import java.util.Set;
import java.util.UUID;

public interface UnifiedAuthPrincipal {

    UUID getUserId();

    String getEmail();

    State getAccountState();

    Set<Role> getRoles();

    Set<AuthProvider> getAuthProvider();
}
