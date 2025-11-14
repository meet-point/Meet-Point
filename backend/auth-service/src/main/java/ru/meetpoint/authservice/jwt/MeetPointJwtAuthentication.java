package ru.meetpoint.authservice.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.meetpoint.security.starter.data.principal.impl.MeetPointUserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class MeetPointJwtAuthentication implements Authentication {

    @Getter
    @Setter
    private MeetPointUserDetails meetPointUserDetails;

    private final String accessToken;

    private boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return meetPointUserDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return meetPointUserDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return meetPointUserDetails;
    }

    @Override
    public Object getPrincipal() {
        return meetPointUserDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.accessToken;
    }
}
