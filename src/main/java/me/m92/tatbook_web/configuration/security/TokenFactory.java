package me.m92.tatbook_web.configuration.security;

public interface TokenFactory<T> {
    Token refresh(AuthenticatedUserProfile userProfile);
    Token access(AuthenticatedUserProfile userProfile);
    T getAlgorithm();
}
