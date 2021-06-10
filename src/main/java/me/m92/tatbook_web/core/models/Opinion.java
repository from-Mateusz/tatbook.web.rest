package me.m92.tatbook_web.core.models;

public class Opinion {

    private String content;

    private boolean recommends;

    private Opinion opinion;

    private Opinion(String content, boolean recommends) {
        this.content = content;
        this.recommends = recommends;
    }

    public static Opinion create(String content, boolean recommends) {
        return new Opinion(content, recommends);
    }
}
