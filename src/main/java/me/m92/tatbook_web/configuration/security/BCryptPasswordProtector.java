package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.core.models.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordProtector implements PasswordProtector {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public Password protect(String rawPassword) {
        return Password.create(PASSWORD_ENCODER.encode(rawPassword));
    }

    @Override
    public boolean verify(String rawPassword, Password savedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, savedPassword.getValue());
    }
}
