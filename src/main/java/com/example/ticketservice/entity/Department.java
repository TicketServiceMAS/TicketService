package com.example.ticketservice.entity;

import com.example.ticketservice.util.DepartmentName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(name = "name", nullable = false)
    private String departmentName;
    @Column(name = "mailAddress", nullable = false)
    private String mailAddress;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MetricsDepartment> metricsDepartments;

}
