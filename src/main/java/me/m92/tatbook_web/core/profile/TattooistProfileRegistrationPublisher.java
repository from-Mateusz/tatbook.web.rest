package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TattooistProfileRegistrationPublisher extends CustomApplicationEventPublisher<String> {

    public TattooistProfileRegistrationPublisher(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void publish() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publish(Map<Enum, String> parameters) {
        TattooistProfileRegistration tattooistProfileRegistration = new TattooistProfileRegistration(this, parameters);
        eventPublisher.publishEvent(tattooistProfileRegistration);
    }
}
