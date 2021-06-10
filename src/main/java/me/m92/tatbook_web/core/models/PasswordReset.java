package me.m92.tatbook_web.core.models;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class PasswordReset {

    private static final long LONGEVITY = (24 * 60);

    private String token;

    private LocalDateTime expireDate;

    private LocalDateTime useDate;

    private PasswordReset() {}

    private PasswordReset(String token) {
        this.token = token;
        this.expireDate = calculateExpireDate();
    }

    public static PasswordReset create(String token) {
        return new PasswordReset(token);
    }

    private LocalDateTime calculateExpireDate() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plusMinutes(LONGEVITY);
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expireDate);
    }

    public void use(String token) {
        if(token.equals(this.token)) {
            this.useDate = LocalDateTime.now();
        }
    }
}
