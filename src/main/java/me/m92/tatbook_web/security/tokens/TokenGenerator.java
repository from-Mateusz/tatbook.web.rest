package me.m92.tatbook_web.security.tokens;

public interface TokenGenerator<T extends Token> {
    T generate(Object...extras);
}
