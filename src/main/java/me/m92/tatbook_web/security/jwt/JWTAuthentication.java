package me.m92.tatbook_web.security.jwt;

import com.google.gson.annotations.Expose;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class JWTAuthentication implements Authentication {

    @Expose
    private TokenPair tokenPair;

    @Expose
    private Collection<? extends GrantedAuthority> authorities;

    private boolean refreshed;

    @Expose
    private LocalDateTime authenticateDate;

    private JWTAuthentication(TokenPair tokenPair,
                              Collection<? extends GrantedAuthority> authorities) {
        this.tokenPair = tokenPair;
        this.authorities = authorities;
    }

    public static JWTAuthentication refreshed(TokenPair tokenPair, Collection<? extends GrantedAuthority> authorities) {
        JWTAuthentication authentication = authenticated(tokenPair, authorities);
        authentication.setRefreshed(true);
        return authentication;
    }

    public static JWTAuthentication authenticated(TokenPair tokenPair, Collection<? extends GrantedAuthority> authorities) {
        JWTAuthentication authentication = new JWTAuthentication(tokenPair, authorities);
        authentication.setAuthenticated(true);
        return authentication;
    }

    public static JWTAuthentication unauthenticated(TokenPair tokenPair, Collection<? extends GrantedAuthority> authorities) {
        return new JWTAuthentication(tokenPair, authorities);
    }

    public String getAccessToken() {
        return tokenPair.getAccessToken();
    }

    public String getRefreshToken() {
        return tokenPair.getRefreshToken();
    }

    public TokenPair getTokenPair() {
        return new TokenPair(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
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
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return null != authenticateDate;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if(authenticated) {
            this.authenticateDate = LocalDateTime.now();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    public LocalDateTime getAuthenticateDate() {
        return authenticateDate;
    }

    public void setRefreshed(boolean refreshed) {
        this.refreshed = refreshed;
    }

    public boolean isRefreshed() {
        return refreshed;
    }
}
