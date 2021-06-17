package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.common.projection.SimpleFeedback;
import me.m92.tatbook_web.core.models.TattooistProfile;
import me.m92.tatbook_web.core.profile.TattooistProfileRepository;
import me.m92.tatbook_web.i18.MessageDictionary;
import me.m92.tatbook_web.i18.MessageDictionaryCatalog;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultRegistrationService implements RegistrationService {

    private TattooistProfileMapper tattooistProfileMapper;

    private TattooistProfileRepository tattooistProfileRepository;

    private

    @Override
    public Feedback register(ProjectionWrapper personalProfileRegistration) throws FailedOperationException {
        TattooistProfile tattooistProfile =
                tattooistProfileMapper.mapToPersonalProfile(personalProfileRegistration.getProjection());
        tattooistProfileRepository.save(tattooistProfile);
        return null;
    }
}
