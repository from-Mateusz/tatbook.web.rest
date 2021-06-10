package me.m92.tatbook_web.core.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

public abstract class CustomApplicationEventPublisher<V> {

    protected ApplicationEventPublisher eventPublisher;

    @Autowired
    public CustomApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public abstract void publish();

    public abstract void publish(Map<Enum, V> parameters);
}
