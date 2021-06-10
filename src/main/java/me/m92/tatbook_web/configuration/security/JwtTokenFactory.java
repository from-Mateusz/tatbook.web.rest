package me.m92.tatbook_web.configuration.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@PropertySource(value = "classpath:security.configuration.properties")
public class JwtTokenFactory implements TokenFactory<Algorithm> {

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.secret}")
    private String secret;

    private JwtAccessTokenGenerator accessTokenGenerator;

    private JwtRefreshTokenGenerator refreshTokenGenerator;

    private Algorithm algorithm;

    @Autowired
    public JwtTokenFactory(JwtAccessTokenGenerator accessTokenGenerator, JwtRefreshTokenGenerator refreshTokenGenerator) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

    @PostConstruct
    private void createAlgorithm() {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token refresh(AuthenticatedUserProfile userProfile) {
        return refreshTokenGenerator.generate(userProfile, this.algorithm, this.issuer);
    }

    public Token access(AuthenticatedUserProfile userProfile) {
        return accessTokenGenerator.generate(userProfile, this.algorithm, this.issuer);
    }

    @Override
    public Algorithm getAlgorithm() {
        return this.algorithm;
    }
}
