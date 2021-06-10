package me.m92.tatbook_web.api.common.projection;

import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class CollectionFeedback<E> implements Projection {

    private HttpStatus status;

    private List<E> payload;

    public CollectionFeedback() {}

    protected CollectionFeedback(HttpStatus status, List<E> payload) {
        this.status = status;
        this.payload = payload;
    }

    public boolean isOK() {
        return status == HttpStatus.OK;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<E> getPayload() {
        return payload;
    }

    public void setPayload(List<E> payload) {
        this.payload = payload;
    }
}
