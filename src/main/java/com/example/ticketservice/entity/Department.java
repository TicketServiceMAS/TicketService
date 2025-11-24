package com.example.ticketservice.entity;

import com.example.ticketservice.routing.DepartmentName;
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
    private DepartmentName departmentName;
    @Column(name = "mailAddress", nullable = false)
    private String mailAddress;

}
