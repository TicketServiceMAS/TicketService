package com.example.ticketservice.entity;

import com.example.ticketservice.util.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metrics_department")
public class MetricsDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metricsDepartmentID;

    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Back-reference to Metrics (inverse side)
    @JsonBackReference
    @OneToOne(mappedBy = "metricsDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Metrics metrics;
}
