package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.events.DefaultEventPublisher;
import me.m92.tatbook_web.events.PasswordRecoveryEvent;
import me.m92.tatbook_web.logger.ApplicationLogger;
import me.m92.tatbook_web.logger.ConsoleApplicationLogger;
import me.m92.tatbook_web.projection.*;
import me.m92.tatbook_web.security.PasswordSecurityUtil;
import me.m92.tatbook_web.service.email.TokenEmailSender;
import me.m92.tatbook_web.token.*;
import me.m92.tatbook_web.userprofile.ClientProfile;
import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.TattooistProfile;
import me.m92.tatbook_web.userprofile.repository.UserProfileRepository;
import me.m92.tatbook_web.validator.DomainValidationException;
import me.m92.tatbook_web.validator.PasswordChangeDomainValidator;
import me.m92.tatbook_web.validator.PasswordResetDomainValidator;
import me.m92.tatbook_web.validator.PasswordResetTokenDomainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
class DefaultPasswordManagementService implements PasswordManagementService {

    private static final ApplicationLogger LOGGER = new ConsoleApplicationLogger(DefaultPasswordManagementService.class);

    private TokenEmailSender emailSender;

    private TokenGenerator tokenGenerator;

    private EmailAuthorizationTokenRepository tokenRepository;

    private PasswordResetDomainValidator passwordResetDomainValidator;

    private PasswordResetTokenDomainValidator passwordResetTokenDomainValidator;

    private PasswordChangeDomainValidator passwordChangeDomainValidator;

    private DefaultEventPublisher eventPublisher;

    private PasswordSecurityUtil passwordSecurityUtil;

    private UserProfileRepository<ClientProfile> clientProfileRepository;

    private UserProfileRepository<TattooistProfile> tattooistProfileRepository;

    @Autowired
    public DefaultPasswordManagementService(@Qualifier("password.recovery.email.sender") TokenEmailSender emailSender,
                                            @Qualifier("urlFriendlyTokenGenerator") TokenGenerator tokenGenerator,
                                            EmailAuthorizationTokenRepository tokenRepository,
                                            PasswordResetDomainValidator passwordResetDomainValidator,
                                            PasswordResetTokenDomainValidator passwordResetTokenDomainValidator,
                                            PasswordChangeDomainValidator passwordChangeDomainValidator,
                                            DefaultEventPublisher eventPublisher,
                                            PasswordSecurityUtil passwordSecurityUtil,
                                            UserProfileRepository<ClientProfile> clientProfileRepository,
                                            UserProfileRepository<TattooistProfile> tattooistProfileRepository) {
        this.emailSender = emailSender;
        this.tokenGenerator = tokenGenerator;
        this.tokenRepository = tokenRepository;
        this.passwordResetDomainValidator = passwordResetDomainValidator;
        this.passwordResetTokenDomainValidator = passwordResetTokenDomainValidator;
        this.passwordChangeDomainValidator = passwordChangeDomainValidator;
        this.eventPublisher = eventPublisher;
        this.passwordSecurityUtil = passwordSecurityUtil;
        this.clientProfileRepository = clientProfileRepository;
        this.tattooistProfileRepository = tattooistProfileRepository;
    }

    @Override
    @Transactional
    public ApplicationResponse change(PasswordChangeProjection passwordChangeProjection) throws DomainValidationException {
        passwordChangeDomainValidator.validate(passwordChangeProjection, changePasswordTask(passwordChangeProjection));
        return TextApplicationResponse.ok("Your password has been successfully changed");
    }

    private Runnable changePasswordTask(PasswordChangeProjection passwordChangeProjection) {
        final String securedPassword = passwordSecurityUtil.secure(passwordChangeProjection.getNewPassword());
        return () -> {
          tattooistProfileRepository.findById(passwordChangeProjection.getProfileId())
                                    .ifPresentOrElse(tattooistProfile -> {
                                        tattooistProfile.setPassword(securedPassword);
                                        tattooistProfileRepository.save(tattooistProfile);
                                    }, () -> {
                                        clientProfileRepository.findById(passwordChangeProjection.getProfileId())
                                                .ifPresent(clientProfile -> {
                                                    clientProfile.setPassword(securedPassword);
                                                    clientProfileRepository.save(clientProfile);
                                                });
                                    });
        };
    }

    @Override
    @Transactional
    public ApplicationResponse recover(String email) throws Exception {
        LOGGER.info("Recovering user's password: " + email);
        final Email userEmail = Email.of(email);
        final String tokenValue = tokenGenerator.generate(email);
        final EmailAuthorizationToken emailAuthorizationToken = EmailAuthorizationToken.of(AuthorizationTokenCategory.PASSWORD_RECOVERY, tokenValue,
                                                                                            AuthorizationTokenLongevity.LONG, userEmail);
        tokenRepository.save(emailAuthorizationToken);
        eventPublisher.publish(new PasswordRecoveryEvent(this, emailAuthorizationToken));
        return TextApplicationResponse.ok("If email exists, email with password recovery link will be sent");
    }

    @Override
    public ApplicationResponse check(PasswordResetTokenCheckProjection tokenCheckProjection) throws DomainValidationException {
        passwordResetTokenDomainValidator.validate(tokenCheckProjection , null);
        Optional<EmailAuthorizationToken> authorizationTokenOptional = tokenRepository.findByEmailAndTokenValue(Email.of(tokenCheckProjection.getEmail()), tokenCheckProjection.getToken());
        final EmailTokenCheckResultProjection result = new EmailTokenCheckResultProjection();
        authorizationTokenOptional.ifPresent(emailAuthorizationToken -> {
            result.setEmail(emailAuthorizationToken.getEmailAddress());
            result.setToken(emailAuthorizationToken.getValue());
            result.setExpireDate(emailAuthorizationToken.getExpireDate());
            result.setValid(true);
        });
        return ProjectionApplicationResponse.ok(result);
    }

    @Override
    @Transactional
    public ApplicationResponse reset(PasswordResetProjection passwordReset) throws Exception {
        LOGGER.info("Resetting user's password from token: " + passwordReset.getToken());
        passwordResetDomainValidator.validate(passwordReset, createPasswordResetTask(passwordReset));
        return TextApplicationResponse.ok("Your password has been reset successfully");
    }

    @Transactional
    private Runnable createPasswordResetTask(PasswordResetProjection passwordReset) {
        return () -> {
           Email userEmail = Email.of(passwordReset.getEmail());
           EmailAuthorizationToken authorizationToken = tokenRepository.findByEmailAndTokenValue(userEmail, passwordReset.getToken()).get();
           authorizationToken.setUsed();
           String securedPassword = passwordSecurityUtil.secure(passwordReset.getPassword());
           tattooistProfileRepository.findByEmail(userEmail)
                                      .ifPresentOrElse(tattooistProfile -> {
                                          tattooistProfile.setPassword(securedPassword);
                                          tattooistProfileRepository.save(tattooistProfile);
                                      }, () -> {
                                          clientProfileRepository.findByEmail(userEmail)
                                                                .ifPresent(clientProfile -> {
                                                                    clientProfile.setPassword(securedPassword);
                                                                    clientProfileRepository.save(clientProfile);
                                                                });
                                      });

       };
    }
}
