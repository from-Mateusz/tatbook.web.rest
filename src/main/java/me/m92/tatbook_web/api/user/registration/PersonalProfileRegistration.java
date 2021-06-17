package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.projection.Projection;

public class PersonalProfileRegistration implements Projection {

    private String name;

    private String emailAddress;

    private String mobileNumber;

    private String password;

    private boolean tattooist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTattooist() {
        return tattooist;
    }

    public void setTattooist(boolean tattooist) {
        this.tattooist = tattooist;
    }
}
