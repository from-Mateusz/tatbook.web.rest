package me.m92.tatbook_web.api.user.managing;

import me.m92.tatbook_web.api.common.projection.Feedback;

public interface PersonalProfileManager {
    Feedback findProfileById(Long id);
    Feedback findProfileByEmailAddress(String emailAddress);
    Feedback findProfileByMobileNumber(String mobileNumber);
}
