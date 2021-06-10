package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.helpers.CustomApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

public class TattooProjectCommissionPublisher extends CustomApplicationEventPublisher<Long> {

    public TattooProjectCommissionPublisher(ApplicationEventPublisher eventPublisher) {
        super(eventPublisher);
    }

    @Override
    public void publish() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publish(Map<Enum, Long> parameters) {
        TattooProjectCommission event = new TattooProjectCommission(this, parameters);
        eventPublisher.publishEvent(event);
    }
}
