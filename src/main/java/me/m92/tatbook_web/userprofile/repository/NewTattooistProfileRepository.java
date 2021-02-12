package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.TattooistProfile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class NewTattooistProfileRepository implements UserProfileRepository<TattooistProfile> {

    @PersistenceContext
    private EntityManager eM;

    @Override
    public TattooistProfile save(TattooistProfile entity) {
        eM.persist(entity);
        return entity;
    }

    @Override
    public TattooistProfile update(TattooistProfile entity) {
        TattooistProfile updatedEntity = eM.merge(entity);
        return updatedEntity;
    }

    @Override
    public void remove(TattooistProfile entity) {
        eM.remove(entity);
    }

    @Override
    public Optional<TattooistProfile> findById(Long id) {
        TattooistProfile tattooistProfile = eM.find(TattooistProfile.class, id);
        return Optional.ofNullable(tattooistProfile);
    }

    @Override
    public Collection<TattooistProfile> findAll() {
        return eM.createNamedQuery("tattooist.profile.findAll", TattooistProfile.class)
                .getResultList();
    }

    @Override
    public Optional<TattooistProfile> findByEmail(Email email) {
        List<TattooistProfile> results = eM.createNamedQuery("tattooist.profile.findByEmail", TattooistProfile.class)
                .setParameter("email", email)
                .getResultList();
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }
}
