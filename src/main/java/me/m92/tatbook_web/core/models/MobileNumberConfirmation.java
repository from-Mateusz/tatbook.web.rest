package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.configuration.security.Token;

import java.time.LocalDateTime;

public class MobileNumberConfirmation {

    private static final Long timeForVerification = 10L;

    private Token token;

    private LocalDateTime expireDate;

    private LocalDateTime useDate;

    private MobileNumberConfirmation() {}

    private MobileNumberConfirmation(Token token) {
        this.token = token;
    }

    public static MobileNumberConfirmation create(Token token) {
        return new MobileNumberConfirmation(token);
    }

    private LocalDateTime calculateExpireDate() {
        return LocalDateTime.now().plusMinutes(timeForVerification);
    }

    public void complete(String token) {
        if(token.equals(this.token)) {
            this.useDate = LocalDateTime.now();
        }
    }

    public Token getToken() {
        return this.token;
    }

    public boolean isConfirmed() {
        return null != useDate && useDate.isBefore(expireDate);
    }
}
