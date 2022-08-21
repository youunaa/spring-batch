package com.example.batch.redis;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricModel;

public interface RedisService {

    /**
     * Redis key-value 저장
     *
     * @param key
     * @param value
     */
    void addRedisKey(String key, Object value);

    /**
     * Redis 조회
     *
     * @param key
     * @return
     */
    Object getRedisKey(String key);

    void addRedisList(MetricModel metric);

    /**
     *
     * @param key
     * @return
     */
    void getRedisList(String key);
}
