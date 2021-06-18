package me.m92.tatbook_web.configuration.security.tokens;

import me.m92.tatbook_web.configuration.security.AuthenticatedUserProfile;

public interface TokenFactory<T> {
    Token refresh(AuthenticatedUserProfile userProfile);
    Token access(AuthenticatedUserProfile userProfile);
    T getAlgorithm();
}
