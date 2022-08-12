package com.example.batch.redis;

import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Slf4j
@RequiredArgsConstructor
@Service("RedisService")
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis 저장
     * JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 수집
     */
    @Override
    public void setRedisMetricValue() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        vop.set("systemCpuLoad", String.format("%.2f", osBean.getSystemCpuLoad() * 100));
        vop.set("freePhysicalMemorySize", String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024));
        vop.set("totalPhysicalMemorySize", String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024));

        log.info("CPU Usage  : " + vop.get("systemCpuLoad"));
        log.info("Memory Free Space  : " + vop.get("freePhysicalMemorySize"));
        log.info("Memory Total Space  : " + vop.get("totalPhysicalMemorySize"));
    }

}
