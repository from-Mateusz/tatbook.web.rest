package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.ParametersCheckApplicationEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PasswordReset extends ParametersCheckApplicationEvent<String> {

    public enum RequiredParameters {
        EMAIL_ADDRESS
    }

    public PasswordReset(Object source, Map<Enum, String> parameters) {
        super(source, parameters);
    }

    @Override
    protected List<Enum> getRequiredParametersNames() {
        return Arrays.asList(RequiredParameters.values());
    }
}
