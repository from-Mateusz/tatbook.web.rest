package me.m92.tatbook_web.configuration.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class SimplifiedAuthenticatedUserProfileHolder implements AuthenticatedUserProfileHolder {

    @Override
    public Long getId() {
        AuthenticatedUserProfile userProfile = (AuthenticatedUserProfile) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userProfile.getId();
    }

    @Override
    public String getLanguage() {
        AuthenticatedUserProfile userProfile = (AuthenticatedUserProfile) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userProfile.getLanguageCode();
    }
}
