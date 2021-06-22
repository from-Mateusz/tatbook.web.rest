package me.m92.tatbook_web.configuration.security.utils;

import me.m92.tatbook_web.configuration.redis.RedisService;
import me.m92.tatbook_web.configuration.security.PersonalProfileAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

@Service
class PersonalProfileAuthenticationRedisService extends RedisService<PersonalProfileAuthentication> {
    @Autowired
    public PersonalProfileAuthenticationRedisService() {}
}
