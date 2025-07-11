package com.neighbourly.auth.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RedisStore implements CacheStore {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final Duration OTP_TTL = Duration.ofMinutes(5);

    @Override
    public void store(String key, String value) {
        redisTemplate.opsForValue().set(key,value, OTP_TTL);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
