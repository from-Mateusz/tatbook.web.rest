package me.m92.tatbook_web.core.models.validators;

import me.m92.tatbook_web.core.models.PasswordReset;
import me.m92.tatbook_web.core.models.exceptions.PasswordResetFailedException;

public class PasswordResetValidator {
    public void validate(PasswordReset reset, String token) {
        if(reset.isExpired() || !token.equals(reset.getToken())) {
            throw new PasswordResetFailedException();
        }
        reset.use(token);
    }
}
