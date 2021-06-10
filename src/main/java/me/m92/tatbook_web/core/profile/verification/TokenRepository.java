package me.m92.tatbook_web.core.profile.verification;

import me.m92.tatbook_web.core.models.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {}
