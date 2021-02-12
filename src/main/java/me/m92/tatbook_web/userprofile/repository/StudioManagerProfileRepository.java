package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.StudioManagerProfile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
class StudioManagerProfileRepository implements UserProfileRepository<StudioManagerProfile> {

    @Override
    public Optional<StudioManagerProfile> findByEmail(Email email) {
        return Optional.empty();
    }

    @Override
    public StudioManagerProfile save(StudioManagerProfile entity) {
        return null;
    }

    @Override
    public StudioManagerProfile update(StudioManagerProfile entity) {
        return null;
    }

    @Override
    public void remove(StudioManagerProfile entity) {

    }

    @Override
    public Optional<StudioManagerProfile> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<StudioManagerProfile> findAll() {
        return null;
    }
}
