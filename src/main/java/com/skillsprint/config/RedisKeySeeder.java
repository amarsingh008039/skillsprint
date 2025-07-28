package com.skillsprint.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeySeeder {
    @Value("${api.keys.allowed}")
    private String allowedKeysCsv;

    private final StringRedisTemplate redisTemplate;

    public RedisKeySeeder(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    public void loadKeysFromEnv() {
        String[] keys = allowedKeysCsv.split(",");
        for (String key : keys) {
            redisTemplate.opsForValue().set(key.trim(), "active");
        }
    }
}
