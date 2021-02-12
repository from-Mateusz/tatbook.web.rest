package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.NewUserProfileProjection;
import me.m92.tatbook_web.projection.RegistrationConfirmationTokenProjection;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface RegistrationService {
    ApplicationResponse prepareForRegistration(NewUserProfileProjection userProfile);
    ApplicationResponse confirmRegistration(RegistrationConfirmationTokenProjection token, NewUserProfileProjection userProfile) throws DomainValidationException;
}
