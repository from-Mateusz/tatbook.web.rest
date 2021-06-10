package me.m92.tatbook_web.api.common.projection;

import org.springframework.http.HttpStatus;

public class ProjectionFeedback<T extends Projection> extends Feedback<T> {

    private ProjectionFeedback(HttpStatus status, T payload) {
        super(status, payload);
    }

    public static <T extends Projection> ProjectionFeedback<T> ok(T payload) {
        return new ProjectionFeedback<T>(HttpStatus.OK, payload);
    }
}
