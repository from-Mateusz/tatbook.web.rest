package me.m92.tatbook_web.configuration.security.tokens;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@DiscriminatorColumn(name = "token_category")
public class Token {

    private Long id;

    private String value;

    @Column(name = "use_date")
    private LocalDateTime useDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    protected Token() {}

    protected Token(String value) {
        this.value = value;
    }

    boolean isExpired() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(expireDate);
    }

    void changeExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                '}';
    }
}
