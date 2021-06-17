package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.core.models.MobileNumber;

public interface MobileNumberConfirmer extends AuthorizationTokenGenerator {
    boolean verify(String rawToken, MobileNumber mobileNumber);
}
