package me.m92.tatbook_web.configuration.security;

public class CurrentProfileAuthenticationWrapper {

    private PersonalProfileWrapper personalProfileAuthentication;

    public CurrentProfileAuthenticationWrapper(PersonalProfileWrapper personalProfileAuthentication) {
        this.personalProfileAuthentication = personalProfileAuthentication;
    }

    public PersonalProfileWrapper getPersonalProfileAuthentication() {
        return personalProfileAuthentication;
    }
}
