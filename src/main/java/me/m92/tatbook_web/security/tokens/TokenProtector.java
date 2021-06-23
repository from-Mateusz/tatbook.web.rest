package me.m92.tatbook_web.security.tokens;

public interface TokenProtector {
    <T extends Token> T protect(T token);
    boolean verify(Token token, Token protectedToken);
}
