package me.m92.tatbook_web.api.user.managing;

import me.m92.tatbook_web.core.models.MobileNumberConfirmation;

public interface PersonalProfileManager {
    void register(PersonalProfileRegistration registration);
    void repeatMobileNumberConfirmation(String mobileNumber);
    void confirmMobileNumber(MobileNumberConfirmation confirmation);
    void resetPassword();
}
