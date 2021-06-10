package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class MobileNumberConfirmation {

    private static final Long timeForVerification = 10L;

    private String token;

    private LocalDateTime expireDate;

    private LocalDateTime useDate;

    private MobileNumberConfirmation() {}

    public MobileNumberConfirmation(String token) {
        this.token = token;
    }

    public static MobileNumberConfirmation create(String token) {
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

    public boolean isConfirmed() {
        return null != useDate && useDate.isBefore(expireDate);
    }
}
