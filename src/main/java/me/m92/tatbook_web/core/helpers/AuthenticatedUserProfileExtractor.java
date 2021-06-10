package me.m92.tatbook_web.core.helpers;

import me.m92.tatbook_web.configuration.security.AuthenticatedUserProfileHolder;
import me.m92.tatbook_web.core.models.ClientProfile;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.models.TattooistProfile;
import me.m92.tatbook_web.core.profile.PersonalProfileReadOnlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUserProfileExtractor {

    private AuthenticatedUserProfileHolder authenticatedUserProfileHolder;

    private PersonalProfileReadOnlyRepository<ClientProfile> clientProfileRepository;

    private PersonalProfileReadOnlyRepository<TattooistProfile> tattooistProfileRepository;

    @Autowired
    public AuthenticatedUserProfileExtractor(AuthenticatedUserProfileHolder authenticatedUserProfileHolder,
                                             PersonalProfileReadOnlyRepository<ClientProfile> clientProfileRepository,
                                             PersonalProfileReadOnlyRepository<TattooistProfile> tattooistProfileRepository) {
        this.authenticatedUserProfileHolder = authenticatedUserProfileHolder;
        this.clientProfileRepository = clientProfileRepository;
        this.tattooistProfileRepository = tattooistProfileRepository;
    }

    public <T extends PersonalProfile> T extractPersonalProfile(Class<T> personalProfileClass) {
        if(personalProfileClass.isAssignableFrom(ClientProfile.class)) {
            Optional<ClientProfile> possibleClientProfile = clientProfileRepository.findById(authenticatedUserProfileHolder.getId());
            if(possibleClientProfile.isPresent()) {
                return (T)possibleClientProfile.get();
            }
            return null;
        }
        if(personalProfileClass.isAssignableFrom(TattooistProfile.class)) {
            Optional<TattooistProfile> possibleTattooistProfile = tattooistProfileRepository.findById(authenticatedUserProfileHolder.getId());
            if(possibleTattooistProfile.isPresent()) {
                return (T)possibleTattooistProfile.get();
            }
            return null;
        }
        return null;
    }
}
