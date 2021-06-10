package me.m92.tatbook_web.core.profile;

public interface MobileNumberConfirmationCodeProducer {
    MobileConfirmationCode produce(String mobileNumber);
    String hash(String code);
    boolean compare(String original, String hash);
}
