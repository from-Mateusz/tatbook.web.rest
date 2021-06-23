package me.m92.tatbook_web.security.tokens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("mobile_number_confirmation")
public class MobileNumberConfirmationToken extends Token {

    private static final Long LONGEVITY_MINUTES = 15L;

    private MobileNumberConfirmationToken(String value) {
        super(value);
    }

    public static MobileNumberConfirmationToken of(String value) {
        MobileNumberConfirmationToken token = new MobileNumberConfirmationToken(value);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime tokenExpireDate = currentDate.plusMinutes(LONGEVITY_MINUTES);
        token.changeExpireDate(tokenExpireDate);
        return token;
    }
}
