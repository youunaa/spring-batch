package com.example.batch.schedulers;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import com.example.batch.metric.repository.MetricRepository;
import com.example.batch.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExampleScheduler {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final RedisService redisService;
    private final MetricRepository metricRepository;

    @Scheduled(fixedDelay = 5000)
    public void startJob() {
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();

            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            JobExecution jobExecution = jobLauncher.run(job, parameters);

            while (jobExecution.isRunning()) {
                log.info("isRunning...");
            }

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1분 주기로 Metric 데이터 DB 저장
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void saveMetricValue() {
        log.info("Metric DB Insert Start !!!");

        for (MetricType metric : MetricType.values()) {
            List<String> list = redisService.getRedisList(metric.name() + "List");

            log.info("Size : [ " + list.size() + " ] ");

            for (String data : list) {

                log.info("Target : [ " + metric.name() + " ] " + data);

                Metric param = Metric.builder()
                        .type(metric.name())
                        .value(data)
                        .build();
                metricRepository.save(param);
            }
        }

        log.info("Metric DB Insert End !!!");
    }

}
