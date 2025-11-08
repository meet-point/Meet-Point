package ru.meetpoint.security.starter.data.principal;

import ru.meetpoint.security.starter.data.enums.AuthProvider;
import ru.meetpoint.security.starter.data.enums.Role;
import ru.meetpoint.security.starter.data.enums.State;

import java.util.Set;
import java.util.UUID;

public interface UnifiedAuthPrincipal {

    UUID getUserId();

    String getEmail();

    State getAccountState();

    Set<Role> getRoles();

    Set<AuthProvider> getAuthProvider();
}
