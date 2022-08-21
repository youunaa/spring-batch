package com.example.batch.redis;

public interface RedisService {

    /**
     * Redis key-value 저장
     * @param key
     * @param value
     */
    void addRedisKey(String key, Object value);

    /**
     * Redis 조회
     * @param key
     * @return
     */
    Object getRedisKey(String key);
}
