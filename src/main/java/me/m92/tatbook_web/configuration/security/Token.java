package me.m92.tatbook_web.configuration.security;

public class Token {

    private String digest;

    public Token(String digest) {
        this.digest = digest;
    }

    public String getDigest() {
        return digest;
    }
}
