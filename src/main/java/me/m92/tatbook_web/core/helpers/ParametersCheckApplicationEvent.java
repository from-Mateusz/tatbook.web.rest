package me.m92.tatbook_web.core.helpers;

import org.springframework.context.ApplicationEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class ParametersCheckApplicationEvent<V> extends ApplicationEvent {

    private Map<Enum, V> parameters;

    public ParametersCheckApplicationEvent(Object source, Map<Enum, V> parameters) {
        super(source);
        this.setRequiredParameters(parameters);
    }

    private final void setRequiredParameters(Map<Enum, V> parameters) {
        checkRequiredParameters(parameters);
        this.parameters = parameters;
    }

    private final void checkRequiredParameters(Map<Enum, V> parameters) {
        if(!parameters.keySet().containsAll(getRequiredParametersNames())) {
            throw new IllegalArgumentException("Lack of required event params");
        }
    }

    public Map<Enum, V> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    protected abstract List<Enum> getRequiredParametersNames();
}
