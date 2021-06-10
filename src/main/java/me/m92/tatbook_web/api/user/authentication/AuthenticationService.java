package me.m92.tatbook_web.api.user.authentication;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.ProjectionFeedback;

public interface AuthenticationService {
    ProjectionFeedback<Token> authenticate(Credentials credentials) throws FailedOperationException;
}
