package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.configuration.security.jwt.JWTAuthentication;
import me.m92.tatbook_web.configuration.security.jwt.TokenPair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PersonalProfileAuthentication implements Authentication {

    private Long id;

    private JWTAuthentication jwtAuthentication;

    private boolean tampered;

    private PersonalProfileAuthentication(Long id, JWTAuthentication jwtAuthentication) {
        this.id = id;
        this.jwtAuthentication = jwtAuthentication;
    }

    public static PersonalProfileAuthentication create(Long id, JWTAuthentication jwtAuthentication) {
        return new PersonalProfileAuthentication(id, jwtAuthentication);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jwtAuthentication.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return jwtAuthentication.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        jwtAuthentication.setAuthenticated(authenticated);
    }

    @Override
    public String getName() {
        return null;
    }

    public JWTAuthentication getJwtAuthentication() {
        return jwtAuthentication;
    }

    public TokenPair getJwtTokens() {
        return jwtAuthentication.getTokenPair();
    }

    public boolean hasBeenRefreshed() {
        return jwtAuthentication.isRefreshed();
    }
}
