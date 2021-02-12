package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.userprofile_configuration.TattooistProfileConfiguration;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface TattooistProfileConfigurationService {
    ApplicationResponse configure(final Long tattooistProfileId,
                                  final TattooistProfileConfiguration tattooistProfileConfiguration) throws DomainValidationException;
}
