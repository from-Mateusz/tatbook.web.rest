package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailure;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.MobileNumber;
import me.m92.tatbook_web.core.profile.ClientProfileRepository;
import me.m92.tatbook_web.core.profile.TattooistProfileRepository;
import me.m92.tatbook_web.i18.MessageDictionary;

@DataIntegrityGuard
public class RegistrationCorrectnessValidator extends CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> {

    private TattooistProfileRepository tattooistProfileRepository;

    private ClientProfileRepository clientProfileRepository;

    private MessageDictionary messageDictionary;

    @Override
    public ValidationFailureBundle validate(ProjectionWrapper<PersonalProfileRegistration> projection) {
        PersonalProfileRegistration registration = projection.unwrap();
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();
        failureBundle.add(validateEmailAddress(registration.getEmailAddress(), projection.getLanguage()));
        failureBundle.add(validateMobileNumber(registration.getMobileNumber(), projection.getLanguage()));
        failureBundle.combineWith(validateWithAnother(projection));
        return failureBundle;
    }

    private ValidationFailure validateEmailAddress(String emailAddress, String language) {
        if(!EmailAddress.hasValidFormat(emailAddress)) {
            return ValidationFailure.of("emailAddress",
                    emailAddress,
                    messageDictionary.findMessage("error_wrong_email_address", language));
        }

        EmailAddress validEmailAddress = EmailAddress.of(emailAddress);
        if(tattooistProfileRepository.findByEmailAddress(validEmailAddress).isPresent()) {
            return ValidationFailure.of("emailAddress",
                    emailAddress,
                    messageDictionary.findMessage("error_wrong_email_address", language));
        }

        if(clientProfileRepository.findByEmailAddress(validEmailAddress).isPresent()) {
            return ValidationFailure.of("emailAddress",
                    emailAddress,
                    messageDictionary.findMessage("error_wrong_email_address", language));
        }

        return null;
    }

    private ValidationFailure validateMobileNumber(String mobileNumber, String language) {
        if(!MobileNumber.hasValidFormat(mobileNumber)) {
            return ValidationFailure.of("mobileNumber",
                    mobileNumber,
                    messageDictionary.findMessage("error_wrong_mobile_number", language));
        }

        MobileNumber validMobileNumber = MobileNumber.of(mobileNumber);
        if(tattooistProfileRepository.findByMobileNumber(validMobileNumber).isPresent()) {
            return ValidationFailure.of("mobileNumber",
                    mobileNumber,
                    messageDictionary.findMessage("error_wrong_mobile_number", language));
        }

        if(clientProfileRepository.findByMobileNumber(validMobileNumber).isPresent()) {
            return ValidationFailure.of("mobileNumber",
                    mobileNumber,
                    messageDictionary.findMessage("error_wrong_mobile_number", language));
        }

        return null;
    }
}
