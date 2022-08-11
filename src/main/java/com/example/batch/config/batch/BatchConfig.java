package com.example.batch.config.batch;

import com.example.batch.model.Metric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final RedisTemplate<String, String> redisTemplate;

    // exampleJob 생성
    @Bean
    public Job exampleJob() throws Exception {
        return jobBuilderFactory.get("exampleJob")
                .start(exampleStep())
                .build();
    }

    // exampleStep 생성
    @Bean
    @JobScope
    public Step exampleStep() throws Exception {
        return stepBuilderFactory.get("job 1 ")
                .<Metric, Metric>chunk(10)
                .reader(reader(null))
                .processor(processor(null))
                .writer(writer(null))
                .build();

// 		### Tasklet Example ###
//		return stepBuilderFactory.get("exampleStep")
//				.tasklet(new ExampleTasklet()).build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Metric> reader(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        log.info("==> reader value : " + requestDate);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("type", "cpu");

        return new JpaPagingItemReaderBuilder<Metric>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .queryString("SELECT value FROM metric WHERE type = : type")
                .entityManagerFactory(entityManagerFactory)
                .name("JpaPagingItemReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Metric, Metric> processor(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new ItemProcessor<Metric, Metric>() {
            @Override
            public Metric process(Metric metric) throws Exception {

                log.info("==> processor Market : " + metric);
                log.info("==> processor value : " + requestDate);

                // 100원 추가
                metric.setValue(metric.getValue() + 100);

                return metric;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Metric> writer(@Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info("==> writer value : " + requestDate);

        return new JpaItemWriterBuilder<Metric>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}