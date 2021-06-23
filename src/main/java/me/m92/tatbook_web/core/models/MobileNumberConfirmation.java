package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.security.tokens.BCryptTokenProtector;
import me.m92.tatbook_web.security.tokens.MobileNumberConfirmationToken;
import me.m92.tatbook_web.security.tokens.TokenProtector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class MobileNumberConfirmation {

    @Transient
    private final TokenProtector tokenProtector;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MobileNumberConfirmationToken> tokens = new ArrayList<>();

    private MobileNumberConfirmation() {
        tokenProtector = new BCryptTokenProtector();
    }

    private MobileNumberConfirmation(MobileNumberConfirmationToken token,
                                     TokenProtector tokenProtector) {
        this.tokenProtector = tokenProtector;
        this.tokens.add(token);
    }

    public static MobileNumberConfirmation create(MobileNumberConfirmationToken token) {
        return new MobileNumberConfirmation(token, new BCryptTokenProtector());
    }

    public static MobileNumberConfirmation create(MobileNumberConfirmationToken token,
                                                  TokenProtector tokenProtector) {
        return new MobileNumberConfirmation(token, tokenProtector);
    }

    void addToken(MobileNumberConfirmationToken token) {
        this.tokens.add(tokenProtector.protect(token));
    }

    boolean confirm(MobileNumberConfirmationToken token) {
        for(MobileNumberConfirmationToken confirmableToken : getConfirmableTokens()) {
            if(tokenProtector.verify(token, confirmableToken)) {
                return true;
            }
        }
        return false;
    }

    public List<MobileNumberConfirmationToken> getConfirmableTokens() {
        return tokens.stream()
                .filter(token -> !token.isExpired())
                .collect(Collectors.toUnmodifiableList());
    }
}
