package me.m92.tatbook_web.configuration.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import me.m92.tatbook_web.configuration.security.PersonalProfileWrapper;
import me.m92.tatbook_web.configuration.security.utils.Moment;
import me.m92.tatbook_web.core.models.PersonalProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultJWTManager implements JWTManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultJWTManager.class);

    private EncodedKeyTool encodedKeyTool;

    private JWTPublicKeyRepository jwtPublicKeyRepository;

    @Override
    public String generateJWT(PersonalProfile personalProfile, TokenGoal tokenGoal) {
        KeyPair keyPair = encodedKeyTool.generate();
        JWTCreator.Builder jwtBuilder = JWT.create()
                                        .withSubject(personalProfile.getEmailAddress())
                                        .withIssuedAt(Moment.getInstance().getDate())
                                        .withClaim("roles", personalProfile.getRoles());
        switch(tokenGoal) {
            case ACCESS: jwtBuilder.withExpiresAt(Moment.getInstance().delayByMinutes(10L).getDate()); break;
            case REFRESH: jwtBuilder.withExpiresAt(Moment.getInstance().delayByMinutes(60L).getDate()); break;
            default: break;
        }
        String token = jwtBuilder.sign(Algorithm.RSA256(null, (RSAPrivateKey) keyPair.getPrivate()));
        jwtPublicKeyRepository.save(SecurePublicKey.create(personalProfile, keyPair.getPublic().getEncoded()));
        return token;
    }

    @Override
    public TokenVerification verifyJWT(PersonalProfile personalProfile, String token) {
        Optional<SecurePublicKey> possibleJWTPublicKey = jwtPublicKeyRepository.findByPersonalProfileId(personalProfile.getId());
        if(!possibleJWTPublicKey.isPresent()) {
            throw new JWTVerificationException("Could not find public key!");
        }
        SecurePublicKey securePublicKey = possibleJWTPublicKey.get();
        PublicKey publicKey = encodedKeyTool.recreatePublicKey(securePublicKey.getKey());
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256((RSAPublicKey) publicKey, null)).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            if(Moment.getInstance().getTime() > decodedJWT.getExpiresAt().getTime()) {
                securePublicKey.retire(true);
                return TokenVerification.RETIRED;
            }
        } catch(JWTVerificationException ex) {
            LOGGER.error("Possibly tampered token of authenticated personal profile: " + personalProfile, ex);
            securePublicKey.retire(true);
            return TokenVerification.TAMPERED;
        }
        return TokenVerification.CORRECT;
    }
}
