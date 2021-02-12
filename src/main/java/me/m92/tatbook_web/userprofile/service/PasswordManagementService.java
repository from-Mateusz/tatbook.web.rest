package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;
import me.m92.tatbook_web.projection.PasswordChangeProjection;
import me.m92.tatbook_web.projection.PasswordResetProjection;
import me.m92.tatbook_web.projection.PasswordResetTokenCheckProjection;
import me.m92.tatbook_web.validator.DomainValidationException;

public interface PasswordManagementService {
    ApplicationResponse change(PasswordChangeProjection passwordChangeProjection) throws DomainValidationException;
    ApplicationResponse recover(String email) throws Exception;
    ApplicationResponse check(PasswordResetTokenCheckProjection projection) throws DomainValidationException;
    ApplicationResponse reset(PasswordResetProjection passwordReset) throws Exception;
}
