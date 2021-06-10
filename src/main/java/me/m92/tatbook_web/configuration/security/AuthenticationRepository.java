package me.m92.tatbook_web.configuration.security;

import java.util.Optional;

public interface AuthenticationRepository {
    Optional<AuthenticatedUserProfile> findTokenOwnerByEmail(String email);
}
