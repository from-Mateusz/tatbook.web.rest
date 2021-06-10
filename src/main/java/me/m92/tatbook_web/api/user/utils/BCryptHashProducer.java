package me.m92.tatbook_web.api.user.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptHashProducer implements PasswordHashProducer{

    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptHashProducer() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String produce(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean checkEquality(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
