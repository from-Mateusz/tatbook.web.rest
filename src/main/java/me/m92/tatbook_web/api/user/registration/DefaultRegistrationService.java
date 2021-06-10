package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.SimpleFeedback;
import me.m92.tatbook_web.i18.MessageDictionary;
import me.m92.tatbook_web.i18.MessageDictionaryCatalog;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultRegistrationService implements RegistrationService {

//    private MobileNumberVerificationRegistry mobileNumberVerificationRegistry;
//
//    private MessageDictionary dictionary;
//
//    @Autowired
//    public DefaultRegistrationService(MobileNumberVerificationRegistry mobileNumberVerificationRegistry, MessageDictionaryCatalog dictionaryCatalog) {
//        this.mobileNumberVerificationRegistry = mobileNumberVerificationRegistry;
//        this.dictionary = dictionaryCatalog.getByCategory(MessageDictionaryCatalog.Category.ERROR);
//    }
//
//    @Override
//    public Feedback askForMobileNumberConfirmation(String mobileNumber) {
//        mobileNumberVerificationRegistry.register(mobileNumber);
//        return null;
//    }
//
//    @Override
//    public Feedback reviewMobileNumber(String mobileNumber, String token) throws FailedOperationException {
//        if(!mobileNumberVerificationRegistry.verify(mobileNumber, token)) {
//            throw new FailedOperationException();
//        }
//        return SimpleFeedback.ok("");
//    }
//
//    @Override
//    public Feedback register() {
//        return null;
//    }


    @Override
    public Feedback register() throws FailedOperationException {
        return null;
    }
}
