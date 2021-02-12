package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.events.DefaultEventPublisher;
import me.m92.tatbook_web.events.RegistrationConfirmationTokenEvent;
import me.m92.tatbook_web.events.UserProfileRegistration;
import me.m92.tatbook_web.logger.ApplicationLogger;
import me.m92.tatbook_web.logger.ConsoleApplicationLogger;
import me.m92.tatbook_web.projection.*;
import me.m92.tatbook_web.security.PasswordSecurityUtil;
import me.m92.tatbook_web.service.InMemoryStoreService;
import me.m92.tatbook_web.token.*;
import me.m92.tatbook_web.userprofile.ClientProfile;
import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.TattooistProfile;
import me.m92.tatbook_web.userprofile.mapper.NewClientProfileMapper;
import me.m92.tatbook_web.userprofile.mapper.NewTattooistProfileMapper;
import me.m92.tatbook_web.userprofile.repository.ClientProfileRepository;
import me.m92.tatbook_web.userprofile.repository.TattooistProfileRepository;
import me.m92.tatbook_web.validator.DomainValidationException;
import me.m92.tatbook_web.validator.DomainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
class DefaultRegistrationService implements RegistrationService {

    private final static ApplicationLogger LOGGER = new ConsoleApplicationLogger(DefaultRegistrationService.class);

    private DefaultEventPublisher eventPublisher;

    private NewTattooistProfileMapper tattooistProfileMapper;

    private NewClientProfileMapper clientProfileMapper;

    private PasswordSecurityUtil passwordSecurityUtil;

    private TokenGenerator tokenGenerator;

    private EmailAuthorizationTokenRepository emailAuthorizationTokenRepository;

    private TattooistProfileRepository tattooistProfileRepository;

    private ClientProfileRepository clientProfileRepository;

    private DomainValidator tokenDomainValidator;

    private InMemoryStoreService inMemoryStoreService;

    @Autowired
    public DefaultRegistrationService(DefaultEventPublisher eventPublisher,
                                      @Qualifier("sms.friendly") TokenGenerator tokenGenerator,
                                      EmailAuthorizationTokenRepository emailAuthorizationTokenRepository,
                                      TattooistProfileRepository tattooistProfileRepository,
                                      ClientProfileRepository clientProfileRepository,
                                      @Qualifier("domain.validator.user.profile.registration.token.confirmation") DomainValidator tokenDomainValidator,
                                      NewTattooistProfileMapper tattooistProfileMapper,
                                      NewClientProfileMapper clientProfileMapper,
                                      PasswordSecurityUtil passwordSecurityUtil,
                                      @Qualifier("new.user.profile.ims") InMemoryStoreService inMemoryStoreService
                                ) {
        this.eventPublisher = eventPublisher;
        this.tokenGenerator = tokenGenerator;
        this.emailAuthorizationTokenRepository = emailAuthorizationTokenRepository;
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.tokenDomainValidator = tokenDomainValidator;
        this.tattooistProfileMapper = tattooistProfileMapper;
        this.clientProfileMapper = clientProfileMapper;
        this.passwordSecurityUtil = passwordSecurityUtil;
        this.inMemoryStoreService = inMemoryStoreService;
    }

    @Override
    public ApplicationResponse prepareForRegistration(NewUserProfileProjection userProfile) {
        final EmailAuthorizationToken authorizationToken = createAuthorizationToken(userProfile);
        final String storeKey = inMemoryStoreService.write(PreRegisteredNewUserProfileProjection.from(userProfile, authorizationToken.getValue()));
        this.sendAuthorizationToken(authorizationToken);
        return ValueApplicationResponse.ok(InMemoryStoreKey.of(storeKey));
    }

    private EmailAuthorizationToken createAuthorizationToken(NewUserProfileProjection userProfile) {
        Email email = Email.of(userProfile.getEmail());
        EmailAuthorizationToken token = EmailAuthorizationToken.of(AuthorizationTokenCategory.NEW_PROFILE_REGISTRATION,
                                                                    tokenGenerator.generate(email.getAddress()),
                                                                    AuthorizationTokenLongevity.SHORT,
                                                                    email);
        emailAuthorizationTokenRepository.save(token);
        return token;
    }

    private void sendAuthorizationToken(EmailAuthorizationToken authorizationToken) {
        eventPublisher.publish(new RegistrationConfirmationTokenEvent(this, authorizationToken));
    }

    @Override
    @Transactional
    public ApplicationResponse confirmRegistration(RegistrationConfirmationTokenProjection token, NewUserProfileProjection userProfile) throws DomainValidationException {
        final DefaultRegistrationService that = this;
        tokenDomainValidator.validate(token, createUserProfileRegistrationTask(userProfile));
        return TextApplicationResponse.ok("User profile has been successfully created!");
    }

    private Runnable createUserProfileRegistrationTask(NewUserProfileProjection userProfile) {
        return () -> {
            switch (userProfile.getRole()) {
                case "tattooist": {
                    TattooistProfile extractedTattooistProfile = tattooistProfileMapper.mapToTattooistProfile(userProfile, passwordSecurityUtil.secure(userProfile.getPassword()));
                    TattooistProfile savedTattooistProfile = tattooistProfileRepository
                            .save(extractedTattooistProfile);
                    eventPublisher.publish(new UserProfileRegistration(this, savedTattooistProfile));
                    break;
                }
                case "client": {
                    ClientProfile extractedClientProfile = clientProfileMapper.mapToClientProfile(userProfile, passwordSecurityUtil.secure(userProfile.getPassword()));
                    ClientProfile savedClientProfile = clientProfileRepository
                            .save(extractedClientProfile);
                    eventPublisher.publish(new UserProfileRegistration(this, savedClientProfile));
                }
                default:
                    break;
            }
        };
    }
}
