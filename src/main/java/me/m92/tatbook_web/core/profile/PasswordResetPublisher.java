package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PasswordResetPublisher extends CustomApplicationEventPublisher<String> {

    @Autowired
    public PasswordResetPublisher(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void publish() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publish(Map<Enum, String> parameters) {
        PasswordReset event = new PasswordReset(this, parameters);
        eventPublisher.publishEvent(event);
    }
}
