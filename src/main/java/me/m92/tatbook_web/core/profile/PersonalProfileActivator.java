package me.m92.tatbook_web.core.profile;

public interface PersonalProfileActivator {
    void register(String mobileNumber);
    void activate(String mobileNumber, String token);
}
