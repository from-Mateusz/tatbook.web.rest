package me.m92.tatbook_web.configuration.security.tokens;

import me.m92.tatbook_web.core.models.Password;

public interface TokenProtector {
    <T extends Token> T protect(T token);
    boolean verify(Token token, Token protectedToken);
}
