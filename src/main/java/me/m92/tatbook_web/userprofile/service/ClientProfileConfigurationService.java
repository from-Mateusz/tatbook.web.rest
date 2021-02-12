package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.EmailChangeProjection;
import me.m92.tatbook_web.projection.HealthConditionProjection;
import me.m92.tatbook_web.projection.TokenProjection;
import me.m92.tatbook_web.projection.userprofile_configuration.ClientProfileConfiguration;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface ClientProfileConfigurationService {
    ApplicationResponse configure(Long clientId, ClientProfileConfiguration clientProfileConfiguration);
    ApplicationResponse changeEmailAddress(Long clientId, EmailChangeProjection emailChangeProjection) throws DomainValidationException;
    ApplicationResponse verifyEmailAddress(TokenProjection tokenProjection) throws DomainValidationException;
    ApplicationResponse receiveHealthCondition(Long clientId, HealthConditionProjection healthConditionProjection);
}
