package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.projection.SimpleFeedback;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.configuration.security.tokens.EmailAddressConfirmationToken;
import me.m92.tatbook_web.configuration.security.tokens.MobileNumberConfirmationToken;
import me.m92.tatbook_web.configuration.security.tokens.MobileNumberConfirmationTokenGenerator;
import me.m92.tatbook_web.configuration.security.tokens.TokenProtector;
import me.m92.tatbook_web.core.models.TattooistProfile;
import me.m92.tatbook_web.core.profile.TattooistProfileRepository;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DefaultRegistrationService implements RegistrationService {

    private TattooistProfileMapper tattooistProfileMapper;

    private TattooistProfileRepository tattooistProfileRepository;

    private RegistrationValidator registrationValidator;

    private MessageDictionary successMessageDictionary;

    private MobileNumberConfirmationTokenGenerator mobileNumberConfirmationTokenGenerator;

    private TokenProtector tokenProtector;

    @Autowired
    public DefaultRegistrationService(TattooistProfileMapper tattooistProfileMapper,
                                      TattooistProfileRepository tattooistProfileRepository,
                                      RegistrationValidator registrationValidator,
                                      @Qualifier("SuccessDict") MessageDictionary successMessageDictionary) {
        this.tattooistProfileMapper = tattooistProfileMapper;
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.registrationValidator = registrationValidator;
        this.successMessageDictionary = successMessageDictionary;
    }

    @Autowired
    public DefaultRegistrationService(TattooistProfileMapper tattooistProfileMapper, TattooistProfileRepository tattooistProfileRepository) {
        this.tattooistProfileMapper = tattooistProfileMapper;
        this.tattooistProfileRepository = tattooistProfileRepository;
    }

    @Override
    public Feedback register(ProjectionWrapper<PersonalProfileRegistration> personalProfileRegistrationWrapper) throws FailedOperationException {
        validate(personalProfileRegistrationWrapper);
        TattooistProfile tattooistProfile = tattooistProfileMapper.mapToPersonalProfile(personalProfileRegistrationWrapper.unwrap());
//        Token token = tokenFactory.create(MOBILE_NUMBER_CONFIRMATION);
//        tattooistProfile.receiveMobileNumberToken(tokenProtector.protect(token));
        MobileNumberConfirmationToken mobileNumberConfirmationToken = mobileNumberConfirmationTokenGenerator.generate();
        MobileNumberConfirmationToken protectedMobileNumberConfirmationToken = tokenProtector.protect(mobileNumberConfirmationToken);
        tattooistProfileRepository.save(tattooistProfile);
        return SimpleFeedback.ok(successMessageDictionary.findMessage("profile_registration_success",
                                                                            personalProfileRegistrationWrapper.getLanguage()));
    }

    private void validate(ProjectionWrapper<PersonalProfileRegistration> personalProfileRegistrationProjectionWrapper) throws FailedOperationException {
        ValidationFailureBundle failureBundle = registrationValidator.validate(personalProfileRegistrationProjectionWrapper);
        if(!failureBundle.isEmpty()) {
            throw new FailedOperationException(failureBundle);
        }
    }
}
