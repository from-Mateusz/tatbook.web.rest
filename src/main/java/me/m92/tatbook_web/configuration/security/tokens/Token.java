package me.m92.tatbook_web.configuration.security.tokens;

public class Token {

    private String digest;

    public Token(String digest) {
        this.digest = digest;
    }

    public static Token create(String value) {
        return new Token(value);
    }

    public String getDigest() {
        return digest;
    }
}
