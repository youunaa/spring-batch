package com.example.batch.redis;

public interface RedisService {

    /**
     * Redis key-value 저장
     * @param key
     * @param value
     */
    void addRedisKey(String key, Object value);

    Object getRedisKey(String key);
}
