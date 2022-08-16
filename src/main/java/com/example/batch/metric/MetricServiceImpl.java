package com.example.batch.metric;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import com.example.batch.metric.repository.MetricRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Slf4j
@RequiredArgsConstructor
@Service("MetricService")
public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;


    /**
     * JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 수집
     */
    @Override
    public void saveMetricValue() {
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

            Metric metric = Metric.builder()
                    .type(type.name())
                    .value(value)
                    .build();
            metricRepository.save(metric);
        }
    }

}
