package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.TattooistProfile;
import org.springframework.data.repository.CrudRepository;

public interface TattooistProfileRepository extends CrudRepository<TattooistProfile, Long> {
}
