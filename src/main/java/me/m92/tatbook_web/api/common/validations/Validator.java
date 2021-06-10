package me.m92.tatbook_web.api.common.validations;

import me.m92.tatbook_web.api.common.projection.Projection;

public interface Validator<T extends Projection> {
    ValidationFailureBundle validate(T projection);
}
