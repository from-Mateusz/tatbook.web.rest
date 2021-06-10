package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;

public interface PersonalProfileRegister<T extends PersonalProfile> {
    boolean register(T profile);
}
