package me.m92.tatbook_web.security.utils;

import me.m92.tatbook_web.configuration.redis.RedisService;
import me.m92.tatbook_web.security.PersonalProfileAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PersonalProfileAuthenticationRedisService extends RedisService<PersonalProfileAuthentication> {
    @Autowired
    public PersonalProfileAuthenticationRedisService() {}
}
