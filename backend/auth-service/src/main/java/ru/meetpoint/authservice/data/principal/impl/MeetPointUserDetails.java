package ru.meetpoint.authservice.data.principal.impl;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.data.enums.AuthProvider;
import ru.meetpoint.authservice.data.enums.Role;
import ru.meetpoint.authservice.data.enums.State;
import ru.meetpoint.authservice.data.principal.UnifiedAuthPrincipal;

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

    public MeetPointUserDetails(UserData userData, Set<AuthProvider> authProviders, String hashedPassword) {
        this.userId = userData.getUserId();
        this.userEmail = userData.getEmail();
        this.hashedPassword = hashedPassword;
        this.accountState = userData.getState();
        this.userRoles = userData.getRoles();
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
