package com.example.ticketservice.entity;

import com.example.ticketservice.routing.PriorityName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "importance", nullable = false)
    private int importance;

    @ElementCollection
    @CollectionTable(
            name = "priority_keywords",
            joinColumns = @JoinColumn(name = "priority_ID")
    )
    @Column(name = "keyword")
    private List<String> keywords;
}
