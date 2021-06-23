package me.m92.tatbook_web.security.passwords;

import me.m92.tatbook_web.core.models.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordProtector implements PasswordProtector {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public Password protect(Password password) {
        return Password.create(PASSWORD_ENCODER.encode(password.getValue()));
    }

    @Override
    public boolean verify(Password password, Password protectedPassword) {
        return PASSWORD_ENCODER.matches(password.getValue(), protectedPassword.getValue());
    }
}
