package me.m92.tatbook_web.security.passwords;

import me.m92.tatbook_web.core.models.Password;

public interface PasswordProtector {
    Password protect(Password password);
    boolean verify(Password password, Password protectedPassword);
}
