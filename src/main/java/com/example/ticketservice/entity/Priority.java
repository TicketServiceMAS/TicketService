package com.example.ticketservice.entity;

import com.example.ticketservice.util.PriorityName;
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
@Table(name = "priority")

public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priorityID;
    @Column(name = "name", nullable = false)
    private PriorityName priorityName;
}
