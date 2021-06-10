package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.CredentialsOwnerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DefaultAuthenticationRepository implements AuthenticationRepository {

    private CredentialsOwnerFinder credentialsOwnerFinder;

    @Autowired
    public DefaultAuthenticationRepository(CredentialsOwnerFinder credentialsOwnerFinder) {
        this.credentialsOwnerFinder = credentialsOwnerFinder;
    }

    @Override
    public Optional<AuthenticatedUserProfile> findTokenOwnerByEmail(String emailAddress) {
        Optional<PersonalProfile> possiblePersonalProfile = credentialsOwnerFinder.findByEmailAddress(emailAddress);

        if(!possiblePersonalProfile.isPresent()) {
            return Optional.ofNullable(AuthenticatedUserProfile.ofUnknown());
        }

        PersonalProfile personalProfile = possiblePersonalProfile.get();
        return Optional.of(AuthenticatedUserProfile.of(personalProfile.getId(),
                                                        personalProfile.getEmailAddress(),
                                                        ""));
    }
}
