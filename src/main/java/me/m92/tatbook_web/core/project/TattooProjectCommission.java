package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.helpers.ParametersCheckApplicationEvent;

import java.util.List;
import java.util.Map;

public class TattooProjectCommission extends ParametersCheckApplicationEvent<Long> {

    public enum RequiredParameters {
        PROJECT_ID,
        TATTOOIST_PROFILE_ID
    }

    public TattooProjectCommission(Object source, Map<Enum, Long> parameters) {
        super(source, parameters);
    }

    @Override
    protected List<Enum> getRequiredParametersNames() {
        return null;
    }
}
