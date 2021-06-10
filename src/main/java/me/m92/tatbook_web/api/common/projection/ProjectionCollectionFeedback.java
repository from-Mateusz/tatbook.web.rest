package me.m92.tatbook_web.api.common.projection;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ProjectionCollectionFeedback<E extends Projection> extends CollectionFeedback<E> {

    public ProjectionCollectionFeedback() {
    }

    public ProjectionCollectionFeedback(HttpStatus status, List<E> payload) {
        super(status, payload);
    }
}
