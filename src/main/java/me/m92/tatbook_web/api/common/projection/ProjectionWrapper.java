package me.m92.tatbook_web.api.common.projection;

public class ProjectionWrapper {

    private Projection projection;

    private String language;

    private ProjectionWrapper(Projection projection, String language) {
        this.projection = projection;
        this.language = language;
    }

    public static ProjectionWrapper of(Projection projection, String language) {
        return new ProjectionWrapper(projection, language);
    }

    public Projection getProjection() {
        return projection;
    }

    public String getLanguage() {
        return language;
    }
}
