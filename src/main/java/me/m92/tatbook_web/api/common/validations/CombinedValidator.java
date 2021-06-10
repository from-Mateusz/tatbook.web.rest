package me.m92.tatbook_web.api.common.validations;


import me.m92.tatbook_web.api.common.projection.Projection;

public abstract class CombinedValidator<T extends Projection> implements Validator<T>, Comparable<CombinedValidator<T>> {

    private CombinedValidator another;

    private Integer order;

    public CombinedValidator() {}

    protected CombinedValidator(Integer order) {
        this.order = order;
    }

    public CombinedValidator combine(CombinedValidator another) {
        this.another = another;
        return another;
    }

    protected ValidationFailureBundle validateWithAnother(T projection) {
        if(null != another) {
            return another.validate(projection);
        }
        return ValidationFailureBundle.ofEmpty();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(CombinedValidator<T> differentValidator) {
        return this.order < differentValidator.order ? -1 : this.order == differentValidator.order ? 0 : 1;
    }
}
