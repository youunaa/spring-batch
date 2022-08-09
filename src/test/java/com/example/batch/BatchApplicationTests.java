package com.example.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

@SpringBootTest
class BatchApplicationTests {

    @Test
    void contextLoads() {

    }


    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        final String key = "sabarada";

        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1"); // redis set 명령어
        final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

        System.out.println("result_1 = " + result_1);

        stringStringValueOperations.increment(key); // redis incr 명령어
        final String result_2 = stringStringValueOperations.get(key);

        System.out.println("result_2 = " + result_2);
    }

}
