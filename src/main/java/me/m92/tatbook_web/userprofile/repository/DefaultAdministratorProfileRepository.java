package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.AdministratorProfile;
import me.m92.tatbook_web.userprofile.Email;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
class DefaultAdministratorProfileRepository implements AdministratorProfileRepository {

    @PersistenceContext
    private EntityManager eM;

    @Override
    public Optional<AdministratorProfile> findByEmail(Email email) {
        List<AdministratorProfile> results = eM.createNamedQuery("administrator.profile.findByEmail", AdministratorProfile.class)
                                                .setParameter("email", email)
                                                .getResultList();
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public AdministratorProfile save(AdministratorProfile entity) {
        eM.persist(entity);
        return entity;
    }

    @Override
    public AdministratorProfile update(AdministratorProfile entity) {
        AdministratorProfile updatedEntity = eM.merge(entity);
        return updatedEntity;
    }

    @Override
    public void remove(AdministratorProfile entity) {
        eM.remove(entity);
    }

    @Override
    public Optional<AdministratorProfile> findById(Long id) {
        List<AdministratorProfile> results = eM.createNamedQuery("administrator.profile.findById")
                                                .setParameter("id", id)
                                                .getResultList();
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Collection<AdministratorProfile> findAll() {
        return null;
    }
}
