package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.security.tokens.BCryptTokenProtector;
import me.m92.tatbook_web.security.tokens.PasswordResetToken;
import me.m92.tatbook_web.security.tokens.TokenProtector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class PasswordReset {

    @Transient
    private final TokenProtector tokenProtector;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetToken> tokens = new ArrayList<>();

    private PasswordReset() {
        tokenProtector = new BCryptTokenProtector();
    }

    private PasswordReset(PasswordResetToken token) {
        this();
        this.tokens.add(token);
    }

    private PasswordReset(PasswordResetToken token, TokenProtector tokenProtector) {
        this.tokens.add(token);
        this.tokenProtector = tokenProtector;
    }

    public static PasswordReset create(PasswordResetToken token) {
        return new PasswordReset(token);
    }

    public static PasswordReset create(PasswordResetToken token, TokenProtector tokenProtector) {
        return new PasswordReset(token, tokenProtector);
    }

    public boolean verify(PasswordResetToken token) {
        for(PasswordResetToken verifiableToken : getVerifiableTokens()) {
            if(tokenProtector.verify(token, verifiableToken)) {
                return true;
            }
        }
        return false;
    }

    private List<PasswordResetToken> getVerifiableTokens() {
        return tokens.stream()
                    .filter(token -> (!token.isExpired() && !token.isUsed()))
                    .collect(Collectors.toUnmodifiableList());
    }
}
