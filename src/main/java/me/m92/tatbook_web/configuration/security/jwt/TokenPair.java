package me.m92.tatbook_web.configuration.security.jwt;

public class TokenPair {

    private String accessToken;

    private String refreshToken;

    protected TokenPair(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenPair create(String accessToken, String refreshToken) {
        return new TokenPair(accessToken, refreshToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
