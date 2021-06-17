package me.m92.tatbook_web.core.profile.verification;

import me.m92.tatbook_web.configuration.security.TokenCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenCategoryRepository extends CrudRepository<TokenCategory, Long> {
    Optional<TokenCategory> findByNameIgnoreCase(String name);
}
