package me.m92.tatbook_web.security.utils;

import me.m92.tatbook_web.security.PersonalProfileAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalProfileAuthenticationCache {

    private static final String KEY_PREFIX = "authentication";

    private PersonalProfileAuthenticationRedisService cacheService;

    @Autowired
    public PersonalProfileAuthenticationCache(PersonalProfileAuthenticationRedisService cacheService) {
        this.cacheService = cacheService;
    }

    public Optional<PersonalProfileAuthentication> findById(Long id) {
        return Optional.ofNullable(cacheService.findCachedValue(KEY_PREFIX.concat(String.valueOf(id))));
    }

    public void cache(PersonalProfileAuthentication authentication) {
        cacheService.cacheValue(KEY_PREFIX.concat(String.valueOf((Long)authentication.getPrincipal())), authentication);
    }
}
