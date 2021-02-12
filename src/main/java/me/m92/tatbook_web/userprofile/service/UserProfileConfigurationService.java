package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.HealthConditionProjection;
import me.m92.tatbook_web.projection.userprofile_configuration.ClientProfileConfiguration;
import me.m92.tatbook_web.projection.userprofile_configuration.TattooistProfileConfiguration;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface UserProfileConfigurationService {
    ApplicationResponse configure(final TattooistProfileConfiguration tattooistProfileConfiguration) throws DomainValidationException;
    ApplicationResponse configure(final ClientProfileConfiguration clientProfileConfiguration);
    ApplicationResponse receiveHealthCondition(final HealthConditionProjection healthConditionProjection);
}
