package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientProfileRegistrationPublisher extends CustomApplicationEventPublisher<String> {

    public ClientProfileRegistrationPublisher(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void publish() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publish(Map<Enum, String> parameters) {
        ClientProfileRegistration clientProfileRegistration = new ClientProfileRegistration(this, parameters);
        eventPublisher.publishEvent(clientProfileRegistration);
    }
}
