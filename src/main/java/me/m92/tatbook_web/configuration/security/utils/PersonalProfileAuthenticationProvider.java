package me.m92.tatbook_web.configuration.security.utils;

import me.m92.tatbook_web.configuration.security.PersonalProfileAuthentication;
import me.m92.tatbook_web.configuration.security.jwt.JWTAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class PersonalProfileAuthenticationProvider implements AuthenticationProvider {

    private PersonalProfileAuthenticationCache cache;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PersonalProfileAuthentication.class).isAssignableFrom(authentication);
    }
}
