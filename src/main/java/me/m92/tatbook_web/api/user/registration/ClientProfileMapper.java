package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.core.models.ClientProfile;
import org.springframework.stereotype.Component;

@Component
public class ClientProfileMapper implements PersonalProfileMapper<ClientProfile> {
    @Override
    public ClientProfile mapToPersonalProfile(PersonalProfileRegistration registration) {
        return null;
    }
}
