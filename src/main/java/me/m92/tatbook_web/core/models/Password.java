package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.security.exceptions.UnencryptedException;
import me.m92.tatbook_web.security.tokens.PasswordResetToken;
import me.m92.tatbook_web.core.models.exceptions.PasswordResetFailedException;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class Password {

    @Column(name = "password")
    private String value;

    @Embedded
    private PasswordReset reset;

    private Password() {}

    private Password(String value) {
        this.value = value;
    }

    public static Password create(String value) {
        return new Password(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void setResetToken(PasswordResetToken token) {
        this.reset = PasswordReset.create(token);
    }

    public Optional<Password> reset(PasswordResetToken token, String newPassword) throws PasswordResetFailedException {
        if(reset.verify(token)) {
            return Optional.ofNullable(Password.create(newPassword));
        }
        return Optional.ofNullable(null);
    }
}
