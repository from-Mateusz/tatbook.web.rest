package me.m92.tatbook_web.security.tokens;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptTokenProtector implements TokenProtector {

    private static final BCryptPasswordEncoder TOKEN_ENCODER = new BCryptPasswordEncoder();

    @Override
    public <T extends Token> T protect(T token) {
        if(token.getClass().isAssignableFrom(MobileNumberConfirmationToken.class)) {
            return (T) MobileNumberConfirmationToken.of(TOKEN_ENCODER.encode(token.getValue()));
        }
        if(token.getClass().isAssignableFrom(EmailAddressConfirmationToken.class)) {
            return (T) EmailAddressConfirmationToken.of(TOKEN_ENCODER.encode(token.getValue()));
        }
        if(token.getClass().isAssignableFrom(PasswordResetToken.class)) {
            return (T) PasswordResetToken.of(TOKEN_ENCODER.encode(token.getValue()));
        }
        return null;
    }

    @Override
    public boolean verify(Token token, Token protectedToken) {
        return TOKEN_ENCODER.matches(token.getValue(), protectedToken.getValue());
    }
}
