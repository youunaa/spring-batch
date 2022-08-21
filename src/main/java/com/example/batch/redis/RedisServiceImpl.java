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

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> redisTemplateList;

    /**
     * Redis 저장
     * @param metricModel
     */
    @Override
    public void addRedisKey(MetricModel metricModel) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(metricModel.getType(), metricModel.getValue());
    }

    /**
     * Redis 조회
     * @param key
     * @return
     */
    @Override
    public String getRedisKey(String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return value;
    }

    /**
     * Redis List 저장
     * @param metric
     */
    @Override
    public void addRedisList(MetricModel metric) {
        ListOperations<String, String> listOps = redisTemplateList.opsForList();
        listOps.rightPush(metric.getType() + "List", metric.getValue());
        // expire time 설정
        redisTemplateList.expire(metric.getType(), 5, TimeUnit.MINUTES);
    }

    /**
     * Redis List 조회
     * @param key
     * @return
     */
    @Override
    public List<String> getRedisList(String key) {
        return redisTemplateList.opsForList().range(key, 0, -1);
    }

}
