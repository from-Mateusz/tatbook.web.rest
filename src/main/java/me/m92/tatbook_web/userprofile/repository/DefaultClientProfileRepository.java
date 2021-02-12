package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.userprofile.ClientProfile;
import me.m92.tatbook_web.userprofile.Email;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
class DefaultClientProfileRepository implements ClientProfileRepository {

    @PersistenceContext
    private EntityManager eM;

    @Override
    public ClientProfile save(ClientProfile entity) {
        eM.merge(entity);
        return entity;
    }

    @Override
    public ClientProfile update(ClientProfile entity) {
        ClientProfile updatedEntity = eM.merge(entity);
        return updatedEntity;
    }

    @Override
    public void remove(ClientProfile entity) {
        eM.remove(entity);
    }

    @Override
    public Optional<ClientProfile> findById(Long id) {
        ClientProfile clientProfile = eM.find(ClientProfile.class, id);
        return Optional.ofNullable(clientProfile);
    }

    @Override
    public Collection<ClientProfile> findAll() {
        return eM.createNamedQuery("client.profile.findAll", ClientProfile.class)
                    .getResultList();
    }

    @Override
    public Optional<ClientProfile> findByEmail(Email email) {
        List<ClientProfile> results = eM.createNamedQuery("client.profile.findByEmail", ClientProfile.class)
                                                .setParameter("email", email)
                                                .getResultList();
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }
}
