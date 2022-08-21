package com.example.batch.metric.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricModel {

    private String type;
    private String value;

}
