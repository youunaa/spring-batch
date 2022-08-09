package com.example.batch.schedulers;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTimedTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        Timer.Sample sample = Timer.start(Metrics.globalRegistry);
        String status = "success";
        try {
            // do some work
        } catch (Exception e) {
            // handle exception
            status = "failure";
        } finally {
            sample.stop(Timer.builder("my.tasklet.timer")
                    .description("Duration of MyTimedTasklet")
                    .tag("status", status)
                    .register(Metrics.globalRegistry));
        }
        return RepeatStatus.FINISHED;
    }
}
