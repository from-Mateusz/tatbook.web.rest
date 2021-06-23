package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.security.tokens.BCryptTokenProtector;
import me.m92.tatbook_web.security.tokens.MobileNumberConfirmationToken;
import me.m92.tatbook_web.security.tokens.TokenProtector;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.List;
import java.util.regex.Pattern;

@Embeddable
public class MobileNumber {

    private static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("\\b[5-8]{1}[0-9]{2}[0-9]{6}\\b");

    @Column(name = "mobile_number")
    private String number;

    @Embedded
    private MobileNumberConfirmation confirmation;

    private MobileNumber() {}

    private MobileNumber(String number) {
        this.number = number;
    }

    private MobileNumber(String number,
                         MobileNumberConfirmationToken token,
                         TokenProtector tokenProtector) {
        this.number = number;
        this.confirmation = MobileNumberConfirmation.create(token, tokenProtector);
    }

    public static MobileNumber of(String number) {
        return of(number, null, new BCryptTokenProtector());
    }

    public static MobileNumber of(String number,
                                  MobileNumberConfirmationToken token,
                                  TokenProtector tokenProtector) {
        if(!hasValidFormat(number)) {
            throw new IllegalArgumentException("Wrong number format");
        }
        return null == token ? new MobileNumber(number) : new MobileNumber(number, token, tokenProtector);
    }

    public static boolean hasValidFormat(String number) {
        return MOBILE_NUMBER_PATTERN.matcher(number).matches();
    }

    public String getNumber() {
        return number;
    }

    boolean confirm(MobileNumberConfirmationToken token) {
        return this.confirmation.confirm(token);
    }

    public List<MobileNumberConfirmationToken> getValidTokens() {
        return this.confirmation.getConfirmableTokens();
    }

    public void receiveFreshToken(MobileNumberConfirmationToken token) {
        if(null == confirmation) {
            this.confirmation = MobileNumberConfirmation.create(token);
        }
        else {
            this.confirmation.addToken(token);
        }
    }
}
