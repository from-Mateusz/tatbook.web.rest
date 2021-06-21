package me.m92.tatbook_web.configuration.security.tokens;

public interface TokenGenerator<T extends Token> {
    T generate(Object...extras);
}
