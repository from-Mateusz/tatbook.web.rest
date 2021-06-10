package me.m92.tatbook_web.api.common.validations;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailureBundle {

    List<ValidationFailure> failures = new ArrayList<>();

    public ValidationFailureBundle() {}

    private ValidationFailureBundle(List<ValidationFailure> failures) {
        this.failures = failures;
    }

    public static ValidationFailureBundle ofEmpty() {
        return new ValidationFailureBundle();
    }

    public void add(ValidationFailure failure) {
        this.failures.add(failure);
    }

    public boolean isEmpty() {
        return failures.isEmpty();
    }

    public void combineWith(ValidationFailureBundle failureBundle) {
        this.failures.addAll(failureBundle.failures);
    }
}
