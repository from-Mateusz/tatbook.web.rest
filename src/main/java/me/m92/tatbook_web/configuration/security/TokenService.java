package me.m92.tatbook_web.configuration.security;

import java.util.Optional;

public interface TokenService {

    Token createRefreshToken(AuthenticatedUserProfile userProfile);

    Token createAccessToken(AuthenticatedUserProfile userProfile);

    Token refreshAccessToken(Token refreshToken);

    Optional<AuthenticatedUserProfile> findOwner(Token accessToken, Token refreshToken);

    boolean shouldRefreshToken(Token accessToken);

    boolean canRefreshToken(Token refreshToken);

    void blacklistToken(Token token);
}
