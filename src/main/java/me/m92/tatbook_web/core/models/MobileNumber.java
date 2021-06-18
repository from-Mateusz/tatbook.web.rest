package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.configuration.security.tokens.Token;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.regex.Pattern;

@Entity
public class MobileNumber {

    private static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("\b[0-9]{9}\b");

    private String number;

    @Embedded
    private MobileNumberConfirmation confirmation;

    private MobileNumber() {}

    private MobileNumber(String number, Token token) {
        this.number = number;
        this.confirmation = MobileNumberConfirmation.create(token);
    }

    public static MobileNumber of(String number) {
        return of(number, null);
    }

    public static MobileNumber of(String number, Token token) {
        if(!hasValidFormat(number)) {
            throw new IllegalArgumentException("Wrong number format");
        }
        return new MobileNumber(number, token);
    }

    public static boolean hasValidFormat(String number) {
        return MOBILE_NUMBER_PATTERN.matcher(number).matches();
    }

    public String getNumber() {
        return number;
    }

    public boolean isConfirmed() {
        return this.confirmation.isConfirmed();
    }

    public void confirm(String token) {
        confirmation.complete(token);
    }

    public Token getToken() {
        return this.confirmation.getToken();
    }

    public void rotateToken(Token token) {
        this.confirmation = MobileNumberConfirmation.create(token);
    }
}
