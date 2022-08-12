package com.example.batch.metric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MetricController {

    private final MetricService metricService;

    @PostMapping("/metric/test")
    public ResponseEntity<?> addRedisKey() {
        metricService.saveMetricValue();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
