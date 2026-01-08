package com.example.ticketservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metrics")
public class Metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metricsID;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metrics_department_id", unique = true, nullable = false)
    @JsonManagedReference
    private MetricsDepartment metricsDepartment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metrics_priority_id", unique = true, nullable = false)
    @JsonManagedReference
    private MetricsPriority metricsPriority;

    @PrePersist
    public void ensureDateIsSet() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
