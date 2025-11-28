package com.example.ticketservice.entity;

import com.example.ticketservice.util.DepartmentName;
import jakarta.persistence.*;
import lombok.*;

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

}
