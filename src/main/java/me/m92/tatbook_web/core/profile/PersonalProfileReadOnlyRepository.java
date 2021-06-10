package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PersonalProfileReadOnlyRepository<T extends PersonalProfile> extends Repository<T, Long> {
    Optional<T> findById(Long id);
    Optional<T> findByMobileNumber(String mobileNumber);
}
