package me.m92.tatbook_web.web.utils;

import me.m92.tatbook_web.api.common.projection.Feedback;
import org.springframework.http.ResponseEntity;

public class FeedbackResponseEntityPreparer {
    public static ResponseEntity<Feedback> prepare(Feedback feedback) {
        return ResponseEntity.status(feedback.getStatus()).body(feedback);
    }
}
