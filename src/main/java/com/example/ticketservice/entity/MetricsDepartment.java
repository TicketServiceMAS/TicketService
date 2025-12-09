package com.example.ticketservice.entity;

import com.example.ticketservice.util.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "metrics_department")
public class MetricsDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metricsDepartmentID;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // ======================================================
    // AUTOMATISK SÆT DATO VED OPRETTELSE
    // ======================================================
    @PrePersist
    public void ensureDateIsSet() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
