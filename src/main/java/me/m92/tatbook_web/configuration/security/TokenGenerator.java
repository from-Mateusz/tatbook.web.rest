package me.m92.tatbook_web.configuration.security;

public interface TokenGenerator {
    Token generate(Object...salt);
}
