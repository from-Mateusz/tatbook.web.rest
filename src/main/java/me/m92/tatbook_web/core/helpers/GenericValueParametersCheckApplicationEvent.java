package me.m92.tatbook_web.core.helpers;

import org.springframework.context.ApplicationEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class GenericValueParametersCheckApplicationEvent<T> extends ApplicationEvent {

    private Map<Enum, T> parameters;

    public GenericValueParametersCheckApplicationEvent(Object source, Map<Enum, T> parameters) {
        super(source);
        this.setRequiredParameters(parameters);
    }

    private final void setRequiredParameters(Map<Enum, T> parameters) {
        checkRequiredParameters(parameters);
        this.parameters = parameters;
    }

    private final void checkRequiredParameters(Map<Enum, T> parameters) {
        if(!parameters.keySet().containsAll(getRequiredParametersNames())) {
            throw new IllegalArgumentException("Lack of required event params");
        }
    }

    public Map<Enum, T> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    protected abstract List<Enum> getRequiredParametersNames();
}
