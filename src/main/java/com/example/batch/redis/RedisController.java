package com.example.batch.redis;

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

@Slf4j
@RequiredArgsConstructor
@RestController
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisService redisService;

    /**
     * Redis 저장
     * JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 수집
     * @return
     */
    @PostMapping("/redisTest")
    public ResponseEntity<?> addRedisKey() {

        redisService.setRedisMetricValue();

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
