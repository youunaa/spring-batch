package com.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
@EnableBatchProcessing // 배치 기능 활성화
@SpringBootApplication
public class BatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
