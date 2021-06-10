package me.m92.tatbook_web.core.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

public abstract class PersonalProfileRegistrationPublisher {

    protected ApplicationEventPublisher eventPublisher;

    @Autowired
    public PersonalProfileRegistrationPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public abstract void publish(Map<Enum, String> parameters);
}
