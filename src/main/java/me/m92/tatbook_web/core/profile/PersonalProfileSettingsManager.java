package me.m92.tatbook_web.core.profile;

public interface PersonalProfileSettingsManager {
    void remindPassword(String emailAddress);
    void resetPassword(String emailAddress, String password);
}
