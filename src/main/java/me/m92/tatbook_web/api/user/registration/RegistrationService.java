package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;

public interface RegistrationService {
    Feedback register() throws FailedOperationException;
}
