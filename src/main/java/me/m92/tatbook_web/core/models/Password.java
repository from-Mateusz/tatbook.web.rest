package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.configuration.security.exceptions.UnencryptedException;
import me.m92.tatbook_web.core.models.exceptions.PasswordResetFailedException;
import me.m92.tatbook_web.core.models.validators.PasswordResetValidator;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Password {

    @Column(name = "password")
    private String value;

    @Column
    @Convert(converter = BooleanIntegerConverter.class)
    public boolean encrypted;

    @Embedded
    private PasswordReset passwordReset;

    private PasswordResetValidator passwordResetValidator = new PasswordResetValidator();

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

    public void setResetToken(String token) {
        this.passwordReset = PasswordReset.create(token);
    }

    public Password reset(String newPassword, String token) throws PasswordResetFailedException {
        passwordResetValidator.validate(passwordReset, token);
        return Password.create(newPassword);
    }

    @PrePersist
    @PreUpdate
    private void isEncrypted() throws UnencryptedException {
        if(!encrypted) {
            throw new UnencryptedException();
        }
    }
}
