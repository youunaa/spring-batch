package com.example.batch.redis;

import com.example.batch.metric.model.MetricModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service("RedisService")
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> redisTemplateList;

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

    @Override
    public void addRedisList(MetricModel metric) {
        ListOperations<String, String> listOps = redisTemplateList.opsForList();
        listOps.rightPush(metric.getType(), metric.getValue());
        // expire time 설정
        redisTemplateList.expire(metric.getType(), 1, TimeUnit.MINUTES);
    }

    @Override
    public void getRedisList(String key) {
        List<String> lists = redisTemplateList.opsForList().range(key, 0, -1);
    }

}
