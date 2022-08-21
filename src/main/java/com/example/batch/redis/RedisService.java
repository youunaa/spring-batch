package com.example.batch.redis;

import com.example.batch.metric.model.MetricModel;

import java.util.List;

public interface RedisService {

    /**
     * Redis 저장
     * @param metricModel
     */
    void addRedisKey(MetricModel metricModel);

    /**
     * Redis 조회
     * @param key
     * @return
     */
    String getRedisKey(String key);

    /**
     * Redis List 저장
     * @param metric
     */
    void addRedisList(MetricModel metric);

    /**
     * Redis List 조회
     * @param key
     * @return
     */
    List<String> getRedisList(String key);

}
