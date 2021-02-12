package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.EmailChangeProjection;
import me.m92.tatbook_web.projection.TokenProjection;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface EmailResetService {
    ApplicationResponse changeEmailAddress(EmailChangeProjection emailChangeProjection) throws DomainValidationException;
    ApplicationResponse verifyChangedEmailAddress(TokenProjection tokenProjection) throws DomainValidationException;
}
