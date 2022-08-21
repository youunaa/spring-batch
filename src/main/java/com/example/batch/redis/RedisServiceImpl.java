package com.example.batch.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("RedisService")
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis key-value 저장
     * @param key
     * @param value
     */
    @Override
    public void addRedisKey(String key, Object value) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(key, value);
    }

    /**
     * Redis 조회
     * @param key
     * @return
     */
    @Override
    public Object getRedisKey(String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        Object value = vop.get(key);
        return value;
    }

}
