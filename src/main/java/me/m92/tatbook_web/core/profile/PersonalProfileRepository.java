package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.MobileNumber;
import me.m92.tatbook_web.core.models.PersonalProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonalProfileRepository<T extends PersonalProfile> extends CrudRepository<T, Long> {
    Optional<T> findByMobileNumber(MobileNumber number);
    Optional<T> findByEmailAddress(EmailAddress emailAddress);
}
