package me.m92.tatbook_web.api.common;

import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;

public class FailedOperationException extends Exception {

    private ValidationFailureBundle failureBundle;

    private String message;

    public FailedOperationException() {
        super();
    }

    public FailedOperationException(ValidationFailureBundle failureBundle) {
        this.failureBundle = failureBundle;
    }

    public ValidationFailureBundle getFailureBundle() {
        return failureBundle;
    }
}
