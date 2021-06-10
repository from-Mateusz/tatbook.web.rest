package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.ParametersCheckApplicationEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TattooistProfileRegistration extends ParametersCheckApplicationEvent<String> {

    public enum RequiredParameters {
        EMAIL_ADDRESS,
        MOBILE_NUMBER,
        SMS_CODE
    }

    public TattooistProfileRegistration(Object source, Map<Enum, String> parameters) {
        super(source, parameters);
    }

    @Override
    protected List<Enum> getRequiredParametersNames() {
        return Collections.unmodifiableList(Arrays.asList(TattooistProfileRegistration.RequiredParameters.values()));
    }
}
