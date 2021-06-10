package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;

import java.util.Optional;

public interface PersonalProfileManager<T extends PersonalProfile> {
    Optional<T> searchById(Long id);
    Optional<T> searchByEmailAddress(String emailAddress);
    boolean verify(String mobileNumber, String token);
}
