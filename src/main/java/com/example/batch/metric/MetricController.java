package com.example.batch.metric;

import com.example.batch.base.BaseController;
import com.example.batch.base.BaseModel;
import com.example.batch.base.BodyModel;
import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/metric")
@RestController
public class MetricController extends BaseController {

    private final MetricService metricService;

    @GetMapping("/{metricType}")
    public BaseModel getJVMMetricInfo(@PathVariable String metricType) {
        Metric metric = new Metric();

        if (MetricType.CPU.name().equals(metricType)) {
            metric = metricService.getJVMMetricInfo(MetricType.CPU);
        }
        if (MetricType.MemorySize.name().equals(metricType)) {
            metric = metricService.getJVMMetricInfo(MetricType.MemorySize);
        }
        if (MetricType.MemoryTotal.name().equals(metricType)) {
            metric = metricService.getJVMMetricInfo(MetricType.MemoryTotal);
        }

        BodyModel body = new BodyModel();
        body.setBody(metric);
        return ok(body);
    }

}
