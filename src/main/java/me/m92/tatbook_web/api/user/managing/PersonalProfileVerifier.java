package me.m92.tatbook_web.api.user.managing;

import me.m92.tatbook_web.api.common.projection.Feedback;

public interface PersonalProfileVerifier {
    Feedback initiateVerification(String mobileNumber);
    Feedback verify(PersonalProfileVerification verification);
}
