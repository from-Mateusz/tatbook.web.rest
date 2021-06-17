package me.m92.tatbook_web.api.user.managing;

public class MobileNumberConfirmation {

    private String mobileNumber;

    private String token;

    public MobileNumberConfirmation(String mobileNumber, String token) {
        this.mobileNumber = mobileNumber;
        this.token = token;
    }
}
