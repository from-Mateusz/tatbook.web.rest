package me.m92.tatbook_web.api.common.projection;

public class ProjectionWrapper<T extends Projection> implements Projection {

    private T projection;

    private String language;

    private ProjectionWrapper(T projection, String language) {
        this.projection = projection;
        this.language = language;
    }

    public static ProjectionWrapper of(Projection projection, String language) {
        return new ProjectionWrapper(projection, language);
    }

    public T unwrap() {
        return projection;
    }

    public String getLanguage() {
        return language;
    }
}
