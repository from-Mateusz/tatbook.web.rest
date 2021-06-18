package me.m92.tatbook_web.configuration.security.tokens;

public interface TokenGenerator {
    Token generate(Object...salt);
}
