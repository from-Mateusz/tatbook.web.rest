package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailure;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.CredentialsOwnerFinder;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@DataIntegrityGuard
public class CredentialsCorrectnessValidator extends CombinedValidator<Credentials> {

    private MessageDictionary messageDictionary;

    private CredentialsOwnerFinder credentialsOwnerFinder;

    @Autowired
    public CredentialsCorrectnessValidator(@Qualifier("ErrorsDict") MessageDictionary messageDictionary,
                                           CredentialsOwnerFinder credentialsOwnerFinder) {
        super(2);
        this.messageDictionary = messageDictionary;
        this.credentialsOwnerFinder = credentialsOwnerFinder;
    }

    @Override
    public ValidationFailureBundle validate(Credentials projection) {
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();
        Optional<PersonalProfile> possiblePersonalProfile = credentialsOwnerFinder.findByEmailAddress(projection.getEmailAddress());

        if(!possiblePersonalProfile.isPresent()) {
            failureBundle.add(ValidationFailure.of("emailAddress",
                    "",
                    messageDictionary.findMessage("authentication_incorrect_credentials",
                            projection.getLanguage())));
        }
        else {
//            PersonalProfile personalProfile = possiblePersonalProfile.get();
//            if(!personalProfile.getPassword().isSameAs(projection.getPassword())) {
//                failureBundle.add(ValidationFailure.of("password",
//                        "",
//                        messageDictionary.findMessage("authentication_incorrect_credentials",
//                                projection.getLanguage())));
//            }
        }

        failureBundle.combineWith(validateWithAnother(projection));
        return failureBundle;
    }
}
