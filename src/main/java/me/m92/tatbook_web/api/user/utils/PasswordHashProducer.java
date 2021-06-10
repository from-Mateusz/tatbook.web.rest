package me.m92.tatbook_web.api.user.utils;

public interface PasswordHashProducer {
    String produce(String password);
    boolean checkEquality(String password, String hash);
}
