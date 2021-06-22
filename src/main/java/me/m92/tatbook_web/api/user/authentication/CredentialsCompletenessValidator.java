package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.validations.CombinedValidator;
import me.m92.tatbook_web.api.common.validations.DataIntegrityGuard;
import me.m92.tatbook_web.api.common.validations.ValidationFailure;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.i18.MessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.springframework.util.StringUtils.*;

@DataIntegrityGuard
public class CredentialsCompletenessValidator extends CombinedValidator<Credentials> {

    private MessageDictionary messageDictionary;

    @Autowired
    public CredentialsCompletenessValidator(@Qualifier("ErrorsDict") MessageDictionary messageDictionary) {
        super(1);
        this.messageDictionary = messageDictionary;
    }

    @Override
    public ValidationFailureBundle validate(Credentials projection) {
        ValidationFailureBundle failureBundle = ValidationFailureBundle.ofEmpty();

//        if(!hasText(projection.getEmailAddress()) || !Email.isPossible(projection.getEmailAddress())) {
//            failureBundle.add(ValidationFailure.of("emailAddress",
//                    "",
//                    messageDictionary.findMessage("error_lack_of_email_address",
//                            projection.getLanguage())));
//        }

        if(!hasText(projection.getPassword())) {
            failureBundle.add(ValidationFailure.of("password",
                    "",
                    messageDictionary.findMessage("error_lack_of_email_address",
                            projection.getLanguage())));
        }

        failureBundle.combineWith(validateWithAnother(projection));
        return failureBundle;
    }
}
