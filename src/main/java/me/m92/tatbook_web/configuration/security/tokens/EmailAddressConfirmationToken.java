package me.m92.tatbook_web.configuration.security.tokens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("email_address_confirmation")
public class EmailAddressConfirmationToken extends Token {

    private static final Long LONGEVITY_MINUTES = (24 * 60L);

    private EmailAddressConfirmationToken(String value) {
        super(value);
    }

    public static EmailAddressConfirmationToken of(String value) {
        EmailAddressConfirmationToken token = new EmailAddressConfirmationToken(value);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime tokenExpireDate = currentDate.plusMinutes(LONGEVITY_MINUTES);
        token.changeExpireDate(tokenExpireDate);
        return token;
    }
}
