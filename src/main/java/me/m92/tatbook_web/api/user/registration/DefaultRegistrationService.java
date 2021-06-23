package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.projection.SimpleFeedback;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.security.tokens.MobileNumberConfirmationToken;
import me.m92.tatbook_web.security.tokens.MobileNumberConfirmationTokenGenerator;
import me.m92.tatbook_web.security.tokens.TokenProtector;
import me.m92.tatbook_web.core.models.ClientProfile;
import me.m92.tatbook_web.core.models.TattooistProfile;
import me.m92.tatbook_web.core.profile.ClientProfileRepository;
import me.m92.tatbook_web.core.profile.TattooistProfileRepository;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultRegistrationService implements RegistrationService {

    private ClientProfileMapper clientProfileMapper;

    private TattooistProfileMapper tattooistProfileMapper;

    private TattooistProfileRepository tattooistProfileRepository;

    private ClientProfileRepository clientProfileRepository;

    private RegistrationValidator registrationValidator;

    private MessageDictionary successMessageDictionary;

    private MobileNumberConfirmationTokenGenerator mobileNumberConfirmationTokenGenerator;

    private TokenProtector tokenProtector;

    @Autowired
    public DefaultRegistrationService(ClientProfileMapper clientProfileMapper,
                                      TattooistProfileMapper tattooistProfileMapper,
                                      TattooistProfileRepository tattooistProfileRepository,
                                      ClientProfileRepository clientProfileRepository,
                                      RegistrationValidator registrationValidator,
                                      @Qualifier("SuccessDict") MessageDictionary successMessageDictionary,
                                      MobileNumberConfirmationTokenGenerator mobileNumberConfirmationTokenGenerator,
                                      TokenProtector tokenProtector) {
        this.clientProfileMapper = clientProfileMapper;
        this.tattooistProfileMapper = tattooistProfileMapper;
        this.tattooistProfileRepository = tattooistProfileRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.registrationValidator = registrationValidator;
        this.successMessageDictionary = successMessageDictionary;
        this.mobileNumberConfirmationTokenGenerator = mobileNumberConfirmationTokenGenerator;
        this.tokenProtector = tokenProtector;
    }

    @Override
    public Feedback register(ProjectionWrapper<PersonalProfileRegistration> personalProfileRegistrationWrapper) throws FailedOperationException {
        validate(personalProfileRegistrationWrapper);
        PersonalProfileRegistration registration = personalProfileRegistrationWrapper.unwrap();
        if(registration.isTattooist()) {
            registerTattooistProfile(registration);
        }
        else {
            registerClientProfile(registration);
        }
        return SimpleFeedback.ok(successMessageDictionary.findMessage("profile_registration_success",
                                                                            personalProfileRegistrationWrapper.getLanguage()));
    }

    private void registerTattooistProfile(PersonalProfileRegistration registration) {
        TattooistProfile tattooistProfile = tattooistProfileMapper.mapToPersonalProfile(registration);
        MobileNumberConfirmationToken mobileNumberConfirmationToken = mobileNumberConfirmationTokenGenerator.generate();
        tattooistProfile.receiveMobileNumberConfirmationToken(mobileNumberConfirmationToken);
        tattooistProfileRepository.save(tattooistProfile);
    }

    private void registerClientProfile(PersonalProfileRegistration registration) {
        ClientProfile clientProfile = clientProfileMapper.mapToPersonalProfile(registration);
        MobileNumberConfirmationToken mobileNumberConfirmationToken = mobileNumberConfirmationTokenGenerator.generate();
        clientProfile.receiveMobileNumberConfirmationToken(mobileNumberConfirmationToken);
        clientProfileRepository.save(clientProfile);
    }

    private void validate(ProjectionWrapper<PersonalProfileRegistration> personalProfileRegistrationProjectionWrapper) throws FailedOperationException {
        ValidationFailureBundle failureBundle = registrationValidator.validate(personalProfileRegistrationProjectionWrapper);
        if(!failureBundle.isEmpty()) {
            throw new FailedOperationException(failureBundle);
        }
    }
}
