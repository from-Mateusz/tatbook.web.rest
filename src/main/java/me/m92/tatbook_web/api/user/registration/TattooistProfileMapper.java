package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.configuration.security.passwords.BCryptPasswordProtector;
import me.m92.tatbook_web.configuration.security.tokens.MobileNumberConfirmationTokenGenerator;
import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.MobileNumber;
import me.m92.tatbook_web.core.models.Password;
import me.m92.tatbook_web.core.models.TattooistProfile;
import org.springframework.stereotype.Component;

@Component
public class TattooistProfileMapper implements PersonalProfileMapper<TattooistProfile> {

    private BCryptPasswordProtector passwordProtector;

    private MobileNumberConfirmationTokenGenerator mobileNumberConfirmationTokenGenerator;

    @Override
    public TattooistProfile mapToPersonalProfile(PersonalProfileRegistration registration) {
        TattooistProfile tattooistProfile =
                TattooistProfile.create(registration.getName(),
                        EmailAddress.of(registration.getEmailAddress()),
                        MobileNumber.of(registration.getMobileNumber()),
                        passwordProtector.protect(Password.create(registration.getPassword())));
        return tattooistProfile;
    }
}
