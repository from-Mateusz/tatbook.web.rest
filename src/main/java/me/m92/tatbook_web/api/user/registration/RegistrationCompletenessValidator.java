package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.ValidationFailure;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

public class RegistrationCompletenessValidator extends CombinedValidator<PersonalProfileRegistration> {

    private MessageDictionary messageDictionary;

    @Autowired
    public RegistrationCompletenessValidator(@Qualifier("ErrorsDict") MessageDictionary messageDictionary) {
        super(0);
        this.messageDictionary = messageDictionary;
    }

    @Override
    public ValidationFailureBundle validate(PersonalProfileRegistration projection) {
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();
        if(!StringUtils.hasText(projection.getEmailAddress())) {
            failureBundle.add(ValidationFailure.of("emailAddress",
                                                        projection.getEmailAddress()),
                    messageDictionary.findMessage("registration.invalid.email.address", ));
        }
        return null;
    }
}
