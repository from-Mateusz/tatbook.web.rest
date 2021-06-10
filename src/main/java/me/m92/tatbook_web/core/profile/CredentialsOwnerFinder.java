package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.ClientProfile;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.models.TattooistProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CredentialsOwnerFinder {

    private PersonalProfileReadOnlyRepository<ClientProfile> clientProfileRepository;

    private PersonalProfileReadOnlyRepository<TattooistProfile> tattooistProfileRepository;

    @Autowired
    public CredentialsOwnerFinder(PersonalProfileReadOnlyRepository<ClientProfile> clientProfileRepository,
                                  PersonalProfileReadOnlyRepository<TattooistProfile> tattooistProfileRepository) {
        this.clientProfileRepository = clientProfileRepository;
        this.tattooistProfileRepository = tattooistProfileRepository;
    }

    public Optional<PersonalProfile> findByEmailAddress(String emailAddress) {
        return Optional.ofNullable(null);
    }
}
