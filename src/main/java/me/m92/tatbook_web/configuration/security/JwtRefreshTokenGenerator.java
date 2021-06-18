package me.m92.tatbook_web.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import me.m92.tatbook_web.configuration.security.tokens.Token;
import me.m92.tatbook_web.configuration.security.utils.Moment;
import org.springframework.stereotype.Service;

@Service
public class JwtRefreshTokenGenerator implements AuthorizationTokenGenerator {

    private final int longevityMinutes = 60;

    @Override
    public Token generate(AuthenticatedUserProfile userProfile, Algorithm algorithm, String issuer) {
        Moment expireMoment = Moment.getInstance().delayByMinutes(longevityMinutes);
        String digest = JWT.create().withIssuer(issuer)
                                    .withSubject(userProfile.getEmailAddress())
                                    .withExpiresAt(expireMoment.getDate())
                                    .withClaim("emailAddress", userProfile.getEmailAddress())
                                    .withClaim("role", "")
                                    .sign(algorithm);
        return new Token(digest);
    }
}
