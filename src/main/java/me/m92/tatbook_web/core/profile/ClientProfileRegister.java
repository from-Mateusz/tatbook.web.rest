package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.ClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientProfileRegister extends CommonPersonalProfileRegister<ClientProfile> {
    @Autowired
    public ClientProfileRegister(MobileNumberConfirmationCodeProducer verificationCodeProducer,
                                 PersonalProfileRepository<ClientProfile> personalProfileRepository,
                                 ClientProfileRegistrationPublisher clientProfileRegistrationPublisher,
                                 MobileNumberConfirmationPublisher mobileNumberVerificationPublisher) {
        super(verificationCodeProducer, personalProfileRepository, clientProfileRegistrationPublisher, mobileNumberVerificationPublisher);
    }
}
