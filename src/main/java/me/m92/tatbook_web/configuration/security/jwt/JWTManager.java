package me.m92.tatbook_web.configuration.security.jwt;

import me.m92.tatbook_web.core.models.PersonalProfile;

public interface JWTManager {

    enum TokenGoal { ACCESS, REFRESH }

    enum TokenVerification { CORRECT, RETIRED, TAMPERED }

    String generateJWT(PersonalProfile personalProfile, TokenGoal tokenGoal);

    TokenVerification verifyJWT(PersonalProfile personalProfile, String token);
}
