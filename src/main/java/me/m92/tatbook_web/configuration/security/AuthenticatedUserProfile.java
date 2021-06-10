package me.m92.tatbook_web.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUserProfile implements UserDetails {

    private Long id;

    private String emailAddress;

    private List<String> roles;

    private AuthenticationToken authenticationToken;

    private String languageCode;

    private LocalDateTime authenticateDate;

    private AuthenticatedUserProfile() {}

    private AuthenticatedUserProfile(Long id, String emailAddress, String role) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public static AuthenticatedUserProfile of(Long id, String email, String role) {
        return new AuthenticatedUserProfile(id, email, role);
    }

    public static AuthenticatedUserProfile ofUnknown() {
        AuthenticatedUserProfile userProfile = new AuthenticatedUserProfile();
        userProfile.setRoles(new ArrayList<>());
        return userProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDateTime getAuthenticateDate() {
        return authenticateDate;
    }

    public void setAuthenticateDate(LocalDateTime authenticateDate) {
        this.authenticateDate = authenticateDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
