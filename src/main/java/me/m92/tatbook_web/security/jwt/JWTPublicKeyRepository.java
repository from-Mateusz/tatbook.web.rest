package me.m92.tatbook_web.security.jwt;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JWTPublicKeyRepository extends CrudRepository<PersonalProfilePublicKey, Long> {
    Optional<PersonalProfilePublicKey> findByPersonalProfileId(Long id);
}
