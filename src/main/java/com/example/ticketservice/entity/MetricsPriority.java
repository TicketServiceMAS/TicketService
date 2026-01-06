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
@Table(name = "metrics_priority")
public class MetricsPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metricsPriorityID;

    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "priorities_id", nullable = false)
    private Priority priority;

    // Back-reference to Metrics (inverse side)
    @JsonBackReference
    @OneToOne(mappedBy = "metricsPriority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Metrics metrics;



}
