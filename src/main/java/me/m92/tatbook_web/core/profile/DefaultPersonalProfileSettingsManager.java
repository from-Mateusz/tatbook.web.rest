package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.ClientProfile;
import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.TattooistProfile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public class DefaultPersonalProfileSettingsManager implements PersonalProfileSettingsManager {

    private PersonalProfileRepository<ClientProfile> clientProfileRepository;

    private PersonalProfileRepository<TattooistProfile> tattooistProfileRepository;

    private PasswordRemindPublisher passwordRemindPublisher;

    private PasswordResetCodeProducer passwordResetCodeProducer;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void remindPassword(String emailAddress) {
        EmailAddress correctEmailAddress = EmailAddress.of(emailAddress);
        Optional<ClientProfile> possibleClientProfile = clientProfileRepository.findByEmailAddress(correctEmailAddress);
        if(possibleClientProfile.isPresent()) {
//            ClientProfile clientProfile = possibleClientProfile.get();
//            PasswordResetCode resetCode = passwordResetCodeProducer.produce(clientProfile);
//            clientProfile.receivePasswordResetCode(resetCode.getHash());
//            clientProfileRepository.save(clientProfile);
//            alertPasswordRemind(clientProfile.getEmailAddress(), resetCode.getOriginal());
        }
        else {
            Optional<TattooistProfile> possibleTattooistProfile = tattooistProfileRepository.findByEmailAddress(correctEmailAddress);
            if(possibleTattooistProfile.isPresent()) {
//                TattooistProfile tattooistProfile = possibleTattooistProfile.get();
//                PasswordResetCode resetCode = passwordResetCodeProducer.produce(tattooistProfile);
//                tattooistProfile.receivePasswordResetCode(resetCode.getHash());
//                tattooistProfileRepository.save(tattooistProfile);
//                alertPasswordRemind(tattooistProfile.getEmailAddress(), resetCode.getOriginal());
            }
        }
    }

    private void alertPasswordRemind(String emailAddress, String resetCode) {
        passwordRemindPublisher.publish(Map.of(
                PasswordRemind.RequiredParameters.EMAIL_ADDRESS, emailAddress,
                PasswordRemind.RequiredParameters.RESET_CODE, resetCode)
        );
    }

    @Override
    @Transactional
    public void resetPassword(String emailAddress, String password) {
        EmailAddress correctEmailAddress = EmailAddress.of(emailAddress);
        String encodedPassword = passwordEncoder.encode(password);
        Optional<ClientProfile> possibleClientProfile = clientProfileRepository.findByEmailAddress(correctEmailAddress);
        if(possibleClientProfile.isPresent()) {
            ClientProfile clientProfile = possibleClientProfile.get();
            clientProfile.changePassword(encodedPassword);
            clientProfileRepository.save(clientProfile);
        }
        else {
            Optional<TattooistProfile> possibleTattooistProfile = tattooistProfileRepository.findByEmailAddress(correctEmailAddress);
            if(possibleTattooistProfile.isPresent()) {
                TattooistProfile tattooistProfile = possibleTattooistProfile.get();
                tattooistProfile.changePassword(encodedPassword);
                tattooistProfileRepository.save(tattooistProfile);
            }
        }
    }
}
