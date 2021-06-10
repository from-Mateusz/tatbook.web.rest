package me.m92.tatbook_web.configuration.security;

public class AuthenticationToken {

    private Token accessToken;

    private Token refreshToken;

    public AuthenticationToken(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    public Token getRefreshToken() {
        return refreshToken;
    }
}
