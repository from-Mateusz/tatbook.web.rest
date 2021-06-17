package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.core.models.Password;

public interface PasswordProtector {
    Password protect(String rawPassword);
    boolean verify(String rawPassword, Password savedPassword);
}
