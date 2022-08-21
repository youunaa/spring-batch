package com.example.batch.metric;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;

public interface MetricService {

    /**
     * JVM(Java Virtual Machine)이 실행 중인 운영 체제에 대한 메트릭 수집
     */
    Metric getJVMMetricInfo(MetricType metricType);

}
