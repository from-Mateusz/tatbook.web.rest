package me.m92.tatbook_web.api.user.registration;

import static org.springframework.util.StringUtils.*;

import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailure;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@DataIntegrityGuard
public class RegistrationCompletenessValidator extends CombinedValidator<ProjectionWrapper<PersonalProfileRegistration>> {

    private MessageDictionary messageDictionary;

    @Autowired
    public RegistrationCompletenessValidator(@Qualifier("ErrorsDict") MessageDictionary messageDictionary) {
        super(0);
        this.messageDictionary = messageDictionary;
    }

    @Override
    public ValidationFailureBundle validate(ProjectionWrapper<PersonalProfileRegistration> projection) {
        PersonalProfileRegistration registration = projection.unwrap();
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();
        if(!hasText(registration.getEmailAddress())) {
            failureBundle.add(ValidationFailure.of("emailAddress",
                    "",
                    messageDictionary.findMessage("error_not_filled_email_address", projection.getLanguage())
                    ));
        }
        if(!hasText(registration.getMobileNumber())) {
            failureBundle.add(ValidationFailure.of("mobileNumber",
                    "",
                    messageDictionary.findMessage("error_not_filled_mobile_number", projection.getLanguage())));
        }
        if(!hasText(registration.getPassword())) {
            failureBundle.add(ValidationFailure.of("password",
                    "",
                    messageDictionary.findMessage("error_not_filled_password", projection.getLanguage())));
        }

        failureBundle.combineWith(validateWithAnother(projection));
        return failureBundle;
    }
}
