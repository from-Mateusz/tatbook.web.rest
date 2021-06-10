package me.m92.tatbook_web.api.common.projection;

import org.springframework.http.HttpStatus;

public abstract class Feedback<T> implements Projection {

    private HttpStatus status;

    private T payload;

    public Feedback() {}

    public Feedback(HttpStatus status, T payload) {
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

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
