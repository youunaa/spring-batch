package com.example.batch.metric;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Slf4j
@RequiredArgsConstructor
@Service("MetricService")
public class MetricServiceImpl implements MetricService {

    /**
     * JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 조회
     * @param metricType
     * @return
     */
    @Override
    public Metric getJVMMetricInfo(MetricType metricType) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        String value = "";

        if (metricType == MetricType.CPU) {
            value = String.format("%.2f", osBean.getSystemCpuLoad() * 100);
        }
        if (metricType == MetricType.MemorySize) {
            value = String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024);
        }
        if (metricType == MetricType.MemoryTotal) {
            value = String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024);
        }

        return Metric.builder()
                .type(metricType.name())
                .value(value)
                .build();
    }

}
