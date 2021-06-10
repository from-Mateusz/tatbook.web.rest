package me.m92.tatbook_web.core.profile.verification;

public interface TokenGenerator {
    String generate(Object...extras);
}
