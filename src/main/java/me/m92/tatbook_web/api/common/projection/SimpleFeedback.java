package me.m92.tatbook_web.api.common.projection;

import org.springframework.http.HttpStatus;

public class SimpleFeedback extends Feedback<String> {

    public SimpleFeedback(HttpStatus status, String payload) {
        super(status, payload);
    }

    public static SimpleFeedback ok(String payload) {
        return new SimpleFeedback(HttpStatus.OK, payload);
    }

    public static SimpleFeedback faulty(String payload) {return new SimpleFeedback(HttpStatus.BAD_REQUEST, payload);}
}
