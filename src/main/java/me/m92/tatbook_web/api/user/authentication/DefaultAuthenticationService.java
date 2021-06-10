package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.ProjectionFeedback;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.configuration.security.jwt.JWTAuthentication;
import me.m92.tatbook_web.configuration.security.jwt.JWTManager;
import me.m92.tatbook_web.configuration.security.jwt.TokenPair;
import me.m92.tatbook_web.configuration.security.utils.PersonalProfileAuthenticationRegistry;
import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.PersonalProfileManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultAuthenticationService implements AuthenticationService {

    private PersonalProfileManager<PersonalProfile> personalProfileManager;

    private CredentialsValidator credentialsValidator;

    private JWTManager jwtManager;

    private PersonalProfileAuthenticationRegistry authenticationRegistry;

    @Override
    public ProjectionFeedback<Token> authenticate(Credentials credentials) throws FailedOperationException {
        reviewCredentials(credentials);
        Optional<PersonalProfile> possiblePersonalProfile = personalProfileManager.searchByEmailAddress(credentials.getEmailAddress());
        if(!possiblePersonalProfile.isPresent()) {
            throw new FailedOperationException();
        }
        PersonalProfile personalProfile = possiblePersonalProfile.get();
        String accessToken = jwtManager.generateJWT(personalProfile, JWTManager.TokenGoal.ACCESS);
        String refreshToken = jwtManager.generateJWT(personalProfile, JWTManager.TokenGoal.REFRESH);
        PersonalProfileAuthenticationRegistry.register(personalProfile.getId(),
                                                        JWTAuthentication.authenticated(TokenPair.create(accessToken, refreshToken),
                                                                                        personalProfile.getRoles().stream()
                                                                                                            .map(role -> new SimpleGrantedAuthority(role))
                                                                                                            .collect(Collectors.toUnmodifiableList())));
        return ProjectionFeedback.ok(Token.create(personalProfile.getId(), accessToken, refreshToken));
    }

    private void reviewCredentials(Credentials credentials) throws FailedOperationException {
        ValidationFailureBundle failureBundle = credentialsValidator.validate(credentials);
        if(!failureBundle.isEmpty()) {
            throw new FailedOperationException(failureBundle);
        }
    }
}
