package com.example.batch.redis;

import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis 테스트
     * cpu, 메모리 사용량 저장
     * @return
     */
    @PostMapping("/redisTest")
    public ResponseEntity<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        vop.set("systemCpuLoad", String.format("%.2f", osBean.getSystemCpuLoad() * 100));
        vop.set("freePhysicalMemorySize", String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024));
        vop.set("totalPhysicalMemorySize", String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024));

        log.info("systemCpuLoad : " + vop.get("systemCpuLoad"));
        log.info("freePhysicalMemorySize : " + vop.get("freePhysicalMemorySize"));
        log.info("totalPhysicalMemorySize : " + vop.get("totalPhysicalMemorySize"));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Redis 조회
     * @param key
     * @return
     */
    @GetMapping("/redisTest/{key}")
    public ResponseEntity<?> getRedisKey(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

}
