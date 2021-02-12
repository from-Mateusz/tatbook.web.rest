package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.events.DefaultEventPublisher;
import me.m92.tatbook_web.events.EmailVerificationEvent;
import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.EmailChangeProjection;
import me.m92.tatbook_web.projection.TextApplicationResponse;
import me.m92.tatbook_web.projection.TokenProjection;
import me.m92.tatbook_web.security.CurrentlyLoggedInUserProfileUtil;
import me.m92.tatbook_web.service.email.TokenEmailSender;
import me.m92.tatbook_web.token.*;
import me.m92.tatbook_web.userprofile.*;
import me.m92.tatbook_web.userprofile.repository.UserProfileRepository;
import me.m92.tatbook_web.validator.DomainValidationException;
import me.m92.tatbook_web.validator.EmailAuthorizationTokenValidator;
import me.m92.tatbook_web.validator.EmailChangeDomainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class DefaultEmailResetService implements EmailResetService {

    private DefaultEventPublisher eventPublisher;

    private TokenGenerator tokenGenerator;

    private EmailAuthorizationTokenRepository emailAuthorizationTokenRepository;

    private EmailChangeDomainValidator emailChangeDomainValidator;

    private TokenEmailSender emailSender;

    private EmailAuthorizationTokenValidator emailAuthorizationTokenValidator;

    private UserProfileRepository<ClientProfile> clientProfileRepository;

    private UserProfileRepository<TattooistProfile> tattooistProfileRepository;

    private UserProfileRepository<StudioManagerProfile> studioManagerProfileRepository;

    private CurrentlyLoggedInUserProfileUtil currentlyLoggedInUserProfileUtil;

    @Autowired
    public DefaultEmailResetService(DefaultEventPublisher eventPublisher,
                                    @Qualifier("urlFriendlyTokenGenerator") TokenGenerator tokenGenerator,
                                    EmailAuthorizationTokenRepository emailAuthorizationTokenRepository,
                                    EmailChangeDomainValidator emailChangeDomainValidator,
                                    @Qualifier("emailVerificationTokenSender") TokenEmailSender emailSender,
                                    EmailAuthorizationTokenValidator emailAuthorizationTokenValidator,
                                    UserProfileRepository<ClientProfile> clientProfileRepository,
                                    UserProfileRepository<TattooistProfile> tattooistProfileRepository,
                                    UserProfileRepository<StudioManagerProfile> studioManagerProfileRepository) {
        this.eventPublisher = eventPublisher;
        this.tokenGenerator = tokenGenerator;
        this.emailAuthorizationTokenRepository = emailAuthorizationTokenRepository;
        this.emailChangeDomainValidator = emailChangeDomainValidator;
        this.emailSender = emailSender;
        this.emailAuthorizationTokenValidator = emailAuthorizationTokenValidator;
        this.clientProfileRepository = clientProfileRepository;
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.studioManagerProfileRepository = studioManagerProfileRepository;
    }

    @Override
    public ApplicationResponse changeEmailAddress(EmailChangeProjection emailChangeProjection) throws DomainValidationException {
        emailChangeDomainValidator.validate(emailChangeProjection, () -> {
                final EmailAuthorizationToken verificationToken = createAuthorizationToken(Email.of(emailChangeProjection.getNewEmail()));
                eventPublisher.publish(new EmailVerificationEvent(this, verificationToken));
            });
        return TextApplicationResponse.ok("Email verification message has been sent");
    }

    private EmailAuthorizationToken createAuthorizationToken(final Email email) {
        final EmailAuthorizationToken authorizationToken = EmailAuthorizationToken.of(AuthorizationTokenCategory.EMAIL_CHANGE,
                                                                                        tokenGenerator.generate(),
                                                                                        AuthorizationTokenLongevity.SHORT,
                                                                                        email);
        emailAuthorizationTokenRepository.save(authorizationToken);
        return authorizationToken;
    }

    @Override
    public ApplicationResponse verifyChangedEmailAddress(TokenProjection tokenProjection) throws DomainValidationException {
        emailAuthorizationTokenValidator.validate(tokenProjection, () -> {
            Optional<EmailAuthorizationToken> authorizationToken = emailAuthorizationTokenRepository.findByTokenValue(tokenProjection.getValue());
            authorizationToken.ifPresent(token -> {
                token.setUsed();
                emailAuthorizationTokenRepository.save(token);

                switch (currentlyLoggedInUserProfileUtil.getRole()) {
                    case TATTOOIST -> {
                        TattooistProfile tattooistProfile = currentlyLoggedInUserProfileUtil.getConcreteUserProfile(TattooistProfile.class);
                        tattooistProfile.setEmail(token.getEmail());
                        tattooistProfileRepository.save(tattooistProfile);
                    }
                    case CLIENT -> {
                        ClientProfile clientProfile = currentlyLoggedInUserProfileUtil.getConcreteUserProfile(ClientProfile.class);
                        clientProfile.setEmail(token.getEmail());
                        clientProfileRepository.save(clientProfile);
                    }
                    case MANAGER -> {
                        StudioManagerProfile studioManagerProfile = currentlyLoggedInUserProfileUtil.getConcreteUserProfile(StudioManagerProfile.class);
                        studioManagerProfile.setEmail(token.getEmail());
                        studioManagerProfileRepository.save(studioManagerProfile);
                    }
                }
            });
        });
        return TextApplicationResponse.ok("Your email has been changed");
    }
}
