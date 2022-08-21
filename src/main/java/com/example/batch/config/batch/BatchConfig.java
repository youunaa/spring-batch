package com.example.batch.config.batch;

import com.example.batch.metric.MetricService;
import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricModel;
import com.example.batch.metric.model.MetricType;
import com.example.batch.redis.RedisService;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.lang.management.ManagementFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MetricService metricService;
    private final RedisService redisService;

    // Job 생성
    @Bean
    public Job stepNextJob() {
        return jobBuilderFactory.get("stepNextJob")
                .start(metricCollect())
                .build();
    }

    /**
     * @return
     */
    @Bean
    public Step metricCollect() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 조회 시작 !!!");

                    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

                    for (MetricType type : MetricType.values()) {
                        String value = "";

                        if (type == MetricType.CPU) {
                            value = String.format("%.2f", osBean.getSystemCpuLoad() * 100);
                        }
                        if (type == MetricType.MemorySize) {
                            value = String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024);
                        }
                        if (type == MetricType.MemoryTotal) {
                            value = String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024);
                        }

                        log.info("Target : [ " + type.name() + " ] : " + value);

                        MetricModel metric = MetricModel.builder()
                                .type(type.name())
                                .value(value)
                                .build();

                        redisService.addRedisList(metric);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
