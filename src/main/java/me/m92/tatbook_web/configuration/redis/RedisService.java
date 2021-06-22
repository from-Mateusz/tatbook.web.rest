package me.m92.tatbook_web.configuration.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.Jackson2HashMapper;

import javax.annotation.Resource;
import java.util.Map;

public abstract class RedisService<T> {

    @Resource(name = "redisTemplate")
    private HashOperations<Object, String, Object> hashOperations;

    private Jackson2HashMapper hashMapper = new Jackson2HashMapper(true);

    public T findCachedValue(String key) {
        Map<String, Object> hash = hashOperations.entries(key);
        T value = (T) hashMapper.fromHash(hash);
        return value;
    }

    public void cacheValue(String key, T value) {
        Map<String, Object> mappedHash = hashMapper.toHash(value);
        hashOperations.putAll(key, mappedHash);
    }
}
