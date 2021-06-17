package me.m92.tatbook_web.core.profile.verification;

import org.springframework.context.ApplicationEvent;

public class MobileNumberVerificationEvent extends ApplicationEvent {
    public MobileNumberVerificationEvent(Object source) {
        super(source);
    }
}
