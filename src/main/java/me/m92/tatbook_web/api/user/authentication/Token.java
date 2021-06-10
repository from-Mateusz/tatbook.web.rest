package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.projection.Projection;

public class Token implements Projection {

    private Long id;

    private String access;

    private String refresh;

    public Token() {}

    private Token(Long id, String access, String refresh) {
        this.id = id;
        this.access = access;
        this.refresh = refresh;
    }

    public static Token create(Long id, String access, String refresh) {
        return new Token(id, access, refresh);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
