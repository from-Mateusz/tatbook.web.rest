package me.m92.tatbook_web.userprofile;

public interface Verifiable<T extends UserProfileVerification> {
    void beginVerification();
    void receiveVerification(T verification);
    boolean isVerified();
    boolean isRejected();
}
