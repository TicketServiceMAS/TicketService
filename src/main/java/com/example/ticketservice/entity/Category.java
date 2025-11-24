package com.example.ticketservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "mailAddress", nullable = false)
    private String mailAddress;
    @ElementCollection
    @CollectionTable(
            name = "category_keywords",
            joinColumns = @JoinColumn(name = "category_ID")
    )
    @Column(name = "keyword")
    private List<String> keywords;
    @Column(name = "importance", nullable = false)
    private int importance;



}
