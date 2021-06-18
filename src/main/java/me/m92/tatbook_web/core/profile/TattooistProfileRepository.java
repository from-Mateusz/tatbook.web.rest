package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.EmailAddress;
import me.m92.tatbook_web.core.models.MobileNumber;
import me.m92.tatbook_web.core.models.TattooistProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TattooistProfileRepository extends PersonalProfileRepository<TattooistProfile> {
}
