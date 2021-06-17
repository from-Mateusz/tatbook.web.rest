package me.m92.tatbook_web.configuration.security;

import com.auth0.jwt.algorithms.Algorithm;

public interface AuthorizationTokenGenerator {
    Token generate(AuthenticatedUserProfile userProfile, Algorithm algorithm, String issuer);
}
