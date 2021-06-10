package me.m92.tatbook_web.configuration.security.jwt;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JWTPublicKeyRepository extends CrudRepository<SecurePublicKey, Long> {
    Optional<SecurePublicKey> findByPersonalProfileId(Long id);
}
