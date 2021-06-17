package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.core.models.PersonalProfile;

public interface PersonalProfileMapper<T extends PersonalProfile> {
    public T mapToPersonalProfile(PersonalProfileRegistration registration);
}
