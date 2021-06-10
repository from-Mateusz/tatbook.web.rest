package me.m92.tatbook_web.configuration.security;

import org.springframework.stereotype.Component;

@Component
public interface AuthenticatedUserProfileHolder {
    Long getId();
    String getLanguage();
}
