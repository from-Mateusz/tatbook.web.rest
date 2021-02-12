package me.m92.tatbook_web.userprofile.repository;

import me.m92.tatbook_web.DomainRepository;
import me.m92.tatbook_web.userprofile.Email;
import me.m92.tatbook_web.userprofile.UserProfile;

import java.util.Optional;

public interface UserProfileRepository<T extends UserProfile> extends DomainRepository<T> {
    Optional<T> findByEmail(Email email);
}
