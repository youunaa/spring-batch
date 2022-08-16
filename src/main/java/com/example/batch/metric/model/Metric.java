package com.example.batch.metric.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "metric")
@Table(name = "metric")
@EntityListeners(AuditingEntityListener.class)
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @CreatedDate
    @Column(name = "create_dt")
    private LocalDateTime createDt;

    private String cpuVal;
    private String memorySize;
    private String memoryTotal;

}
