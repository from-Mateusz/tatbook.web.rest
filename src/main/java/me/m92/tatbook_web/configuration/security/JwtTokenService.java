package me.m92.tatbook_web.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.m92.tatbook_web.configuration.security.utils.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtTokenService implements TokenService {

    private AuthenticationRepository authenticationRepository;

    private JwtTokenFactory tokenFactory;

    private JWTVerifier verifier;

    @Autowired
    public JwtTokenService(AuthenticationRepository authenticationRepository, JwtTokenFactory tokenFactory) {
        this.authenticationRepository = authenticationRepository;
        this.tokenFactory = tokenFactory;
    }

    @PostConstruct
    private void buildVerifier() {
        this.verifier = JWT.require(tokenFactory.getAlgorithm()).build();
    }

    @Override
    public Token createRefreshToken(AuthenticatedUserProfile userProfile) {
        return tokenFactory.refresh(userProfile);
    }

    @Override
    public Token createAccessToken(AuthenticatedUserProfile userProfile) {
        return tokenFactory.access(userProfile);
    }

    public void blacklistToken(Token token) {

    }

    @Override
    public boolean canRefreshToken(Token refreshToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(refreshToken.getDigest());
            return true;
        } catch(JWTVerificationException exception) {
            return false;
        }
    }

    @Override
    public Optional<AuthenticatedUserProfile> findOwner(Token accessToken, Token refreshToken) {
        Optional<AuthenticatedUserProfile> possibleRealOwner = Optional.ofNullable(null);

        try {
            DecodedJWT accessTokenDecodedJWT = verifier.verify(accessToken.getDigest());
            Map<String, Claim> accessTokenClaims = accessTokenDecodedJWT.getClaims();
            String accessTokenEmail = accessTokenClaims.get("emailAddress").asString();

            DecodedJWT refreshTokenDecodedJWT = verifier.verify(accessToken.getDigest());
            Map<String, Claim> refreshTokenClaims = refreshTokenDecodedJWT.getClaims();
            String refreshTokenEmail = refreshTokenClaims.get("emailAddress").asString();

            if(accessTokenEmail.equals(refreshTokenEmail)) {
                possibleRealOwner = authenticationRepository.findTokenOwnerByEmail(accessTokenEmail);
            }
        } catch(JWTVerificationException exception) {
            return possibleRealOwner;
        }

        return possibleRealOwner;
    }

    private Optional<AuthenticatedUserProfile> findOwnerByToken(Token token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token.getDigest());
            Map<String, Claim> claims = decodedJWT.getClaims();
            String email = claims.get("email").asString();

            return authenticationRepository.findTokenOwnerByEmail(email);
        } catch(JWTVerificationException exception) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Token refreshAccessToken(Token refreshToken) {
        return null;
    }

    @Override
    public boolean shouldRefreshToken(Token accessToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(accessToken.getDigest());
            Moment moment = Moment.getInstance();
            long longevityMinutes = ((moment.getTime() - decodedJWT.getIssuedAt().getTime()) / (1000 * 60));
            if(longevityMinutes >= 5) {
                return true;
            }
            else {
                return false;
            }
        } catch (JWTVerificationException ex) {
            return true;
        }
    }
}
