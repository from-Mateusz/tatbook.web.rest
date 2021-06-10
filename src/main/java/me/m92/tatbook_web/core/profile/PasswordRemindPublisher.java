package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

public class PasswordRemindPublisher extends CustomApplicationEventPublisher<String> {

    public PasswordRemindPublisher(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void publish() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publish(Map<Enum, String> parameters) {
        PasswordRemind event = new PasswordRemind(this, parameters);
        eventPublisher.publishEvent(event);
    }
}
