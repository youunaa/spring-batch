package com.example.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class Redis {

    @Autowired
    static StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
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
