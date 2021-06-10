package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.core.models.PersonalProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonalProfileWrapper implements UserDetails {

    private PersonalProfile personalProfile;

    private LocalDateTime authenticationDate;

    private boolean tampered;

    private PersonalProfileWrapper() {}

    private PersonalProfileWrapper(PersonalProfile personalProfile) {
        this.personalProfile = personalProfile;
        this.authenticationDate = LocalDateTime.now();
    }

    public static PersonalProfileWrapper create(PersonalProfile personalProfile) {
        return new PersonalProfileWrapper(personalProfile);
    }

    public PersonalProfile getPersonalProfile() {
        return personalProfile;
    }

    public LocalDateTime getAuthenticationDate() {
        return authenticationDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.personalProfile.getRoles()
                                    .stream()
                                    .map(role -> new SimpleGrantedAuthority(role))
                                    .collect(Collectors.toUnmodifiableList());
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

    public boolean isTampered() {
        return tampered;
    }

    public void setTampered(boolean tampered) {
        this.tampered = tampered;
    }
}
