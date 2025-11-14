package ru.meetpoint.security.starter.data.principal.impl;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.meetpoint.security.starter.data.enums.AuthProvider;
import ru.meetpoint.security.starter.data.enums.Role;
import ru.meetpoint.security.starter.data.enums.State;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MeetPointUserDetails implements UserDetails, CredentialsContainer, UnifiedAuthPrincipal {

    private final UUID userId;

    private final String userEmail;

    private String hashedPassword;

    private final State accountState;

    private final Set<Role> userRoles;

    private final Set<AuthProvider> authProviders;

    public MeetPointUserDetails(UUID userId, String userEmail, String hashedPassword,
                                State accountState, Set<Role> userRoles, Set<AuthProvider> authProviders) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.hashedPassword = hashedPassword;
        this.accountState = accountState;
        this.userRoles = userRoles;
        this.authProviders = authProviders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public void eraseCredentials() {
        this.hashedPassword = null;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public String getEmail() {
        return userEmail;
    }

    @Override
    public State getAccountState() {
        return accountState;
    }

    @Override
    public Set<Role> getRoles() {
        return userRoles;
    }

    @Override
    public Set<AuthProvider> getAuthProvider() {
        return authProviders;
    }
}
