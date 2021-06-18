package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.api.user.authentication.Credentials;
import me.m92.tatbook_web.configuration.security.tokens.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtAuthenticationService implements AuthenticationService {

    private AuthenticationRepository authenticationRepository;

    private JwtTokenService jwtTokenService;

    @Autowired
    public JwtAuthenticationService(AuthenticationRepository authenticationRepository, JwtTokenService jwtTokenService) {
        this.authenticationRepository = authenticationRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public AuthenticationToken authenticate(final Credentials credentials) {
        Optional<AuthenticatedUserProfile> possibleAuthenticatedUserProfile = authenticationRepository.findTokenOwnerByEmail(credentials.getEmailAddress());
        if(possibleAuthenticatedUserProfile.isPresent()) {
            AuthenticatedUserProfile authenticatedUserProfile = possibleAuthenticatedUserProfile.get();
            AuthenticationToken authenticationToken = new AuthenticationToken(jwtTokenService.createAccessToken(authenticatedUserProfile),
                                                                                jwtTokenService.createRefreshToken(authenticatedUserProfile));
            authenticatedUserProfile.setAuthenticationToken(authenticationToken);
            return authenticationToken;
        }
        return null;
    }

    @Override
    public Optional<AuthenticatedUserProfile> findOwnerByTokens(Token accessToken, Token refreshToken) {
        return jwtTokenService.findOwner(accessToken, refreshToken);
    }
}
