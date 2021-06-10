package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import me.m92.tatbook_web.core.models.PersonalProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class CommonPersonalProfileRegister<T extends PersonalProfile> implements PersonalProfileRegister<T> {

    private MobileNumberConfirmationCodeProducer mobileNumberConfirmationCodeProducer;

    private PersonalProfileRepository<T> personalProfileRepository;

    private PersonalProfileActivator personalProfileActivator;

    private CustomApplicationEventPublisher personalProfileRegistrationPublisher;

    MobileNumberConfirmationPublisher mobileNumberConfirmationPublisher;

    @Autowired
    public CommonPersonalProfileRegister(MobileNumberConfirmationCodeProducer mobileNumberConfirmationCodeProducer,
                                         PersonalProfileRepository<T> personalProfileRepository,
                                         CustomApplicationEventPublisher personalProfileRegistrationPublisher,
                                         MobileNumberConfirmationPublisher mobileNumberConfirmationPublisher) {
        this.mobileNumberConfirmationCodeProducer = mobileNumberConfirmationCodeProducer;
        this.personalProfileRepository = personalProfileRepository;
        this.personalProfileRegistrationPublisher = personalProfileRegistrationPublisher;
        this.mobileNumberConfirmationPublisher = mobileNumberConfirmationPublisher;
    }

    @Override
    @Transactional
    public boolean register(T profile) {
//        MobileConfirmationCode confirmationCode = mobileNumberConfirmationCodeProducer.produce(profile.getMobileNumber());
//        profile.receiveMobileNumberConfirmationCode(confirmationCode.getHash());
//        personalProfileRegistrationPublisher.publish(Map.of(ClientProfileRegistration.RequiredParameters.MOBILE_NUMBER, profile.getMobileNumber(),
//                ClientProfileRegistration.RequiredParameters.SMS_CODE, confirmationCode.getOriginal(),
//                ClientProfileRegistration.RequiredParameters.EMAIL_ADDRESS, profile.getEmailAddress()));
//        personalProfileActivator.register(profile.getMobileNumber());
        return true;
    }

//    @Override
//    @Transactional
//    public boolean confirmMobileNumber(MobileNumber number, String code) {
//        Optional<T> possibleProfile = personalProfileRepository.findByMobileNumber(number);
//        boolean confirmedMobileNumber = false;
//        if(possibleProfile.isPresent()) {
//            T profile = possibleProfile.get();
//            profile.confirmMobileNumber(mobileNumberConfirmationCodeProducer.hash(code));
//            mobileNumberConfirmationPublisher.publish(
//                    Map.of(MobileNumberConfirmation.RequiredParameters.EMAIL_ADDRESS, profile.getEmailAddress()));
//            confirmedMobileNumber = profile.hasConfirmedMobileNumber();
//        }
//        return confirmedMobileNumber;
//    }
}
