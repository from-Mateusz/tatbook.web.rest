package me.m92.tatbook_web.core.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class Token {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private Long id;

    private String value;

    private LocalDateTime expireDate;

    private LocalDateTime useDate;

    private TokenCategory tokenCategory;

    private Token() {}

    private Token(String value, LocalDateTime expireDate, TokenCategory tokenCategory) {
        this.id = id;
        this.value = value;
        this.expireDate = expireDate;
        this.tokenCategory = tokenCategory;
    }

    public static Token create(String value, TokenCategory tokenCategory) {
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(tokenCategory.getLongevity());
        return new Token(value, expireDate, tokenCategory);
    }

    public boolean hasSameValue(String value) {
        if(null == id) {
            return value == this.value;
        }
        return ENCODER.matches(value, this.value);
    }

    @PrePersist
    private void hash() {
        if(null == id) {
            ENCODER.encode(value);
        }
    }
}
