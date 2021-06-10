package me.m92.tatbook_web.api.common.validations;

public class ValidationFailure {

    private String fieldName;

    private String erroneousValue;

    private String explanation;

    private ValidationFailure(String fieldName, String erroneousValue, String explanation) {
        this.fieldName = fieldName;
        this.erroneousValue = erroneousValue;
        this.explanation = explanation;
    }

    public static ValidationFailure of(String fieldName, String erroneousValue, String explanation) {
        return new ValidationFailure(fieldName, erroneousValue, explanation);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErroneousValue() {
        return erroneousValue;
    }

    public void setErroneousValue(String erroneousValue) {
        this.erroneousValue = erroneousValue;
    }
}
