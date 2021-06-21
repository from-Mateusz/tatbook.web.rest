package me.m92.tatbook_web.configuration.security.tokens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("password_reset")
public class PasswordResetToken extends Token {

    private static final Long LONGEVITY_MINUTES = (24 * 60L);

    private PasswordResetToken(String value) {
        super(value);
    }

    public static PasswordResetToken of(String value) {
        PasswordResetToken token = new PasswordResetToken(value);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime tokenExpireDate = currentDate.plusMinutes(LONGEVITY_MINUTES);
        token.changeExpireDate(tokenExpireDate);
        return token;
    }
}
