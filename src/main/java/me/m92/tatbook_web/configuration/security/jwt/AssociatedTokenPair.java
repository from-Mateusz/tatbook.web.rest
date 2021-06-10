package me.m92.tatbook_web.configuration.security.jwt;

public class AssociatedTokenPair extends TokenPair {

    private Long id;

    public AssociatedTokenPair(String accessToken, String refreshToken, Long id) {
        super(accessToken, refreshToken);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
