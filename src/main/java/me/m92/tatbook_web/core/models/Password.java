package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.core.models.exceptions.PasswordResetFailedException;
import me.m92.tatbook_web.core.models.validators.PasswordResetValidator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class Password {

    @Column(name = "password")
    private String value;

    @Embedded
    private PasswordReset passwordReset;

    private PasswordResetValidator passwordResetValidator = new PasswordResetValidator();

    private Password() {}

    private Password(String value) {
        this.value = value;
    }

    public static Password create(String body) {
        return new Password(body);
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
}
