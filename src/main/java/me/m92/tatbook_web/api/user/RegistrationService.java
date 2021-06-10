package me.m92.tatbook_web.api.user;


import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.Projection;

public interface RegistrationService<T extends Projection> {
    Feedback register(T profileData) throws FailedOperationException;
}
