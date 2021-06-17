package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.dto.PersonalProfileRegistration;

public interface PersonalProfileRegister {
    boolean register(PersonalProfileRegistration registration);
}
