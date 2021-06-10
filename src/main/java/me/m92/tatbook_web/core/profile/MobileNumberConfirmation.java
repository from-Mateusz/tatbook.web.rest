package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.ParametersCheckApplicationEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MobileNumberConfirmation extends ParametersCheckApplicationEvent<String> {

    enum RequiredParameters {
        EMAIL_ADDRESS
    }

    public MobileNumberConfirmation(Object source, Map<Enum, String> parameters) {
        super(source, parameters);
    }

    @Override
    protected List<Enum> getRequiredParametersNames() {
        return Collections.unmodifiableList(Arrays.asList(MobileNumberConfirmation.RequiredParameters.values()));
    }
}
