package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.api.user.authentication.Credentials;
import me.m92.tatbook_web.configuration.security.tokens.Token;

import java.util.Optional;

public interface AuthenticationService {
    AuthenticationToken authenticate(final Credentials credentials);
    Optional<AuthenticatedUserProfile> findOwnerByTokens(final Token accessToken, final Token refreshToken);
}
