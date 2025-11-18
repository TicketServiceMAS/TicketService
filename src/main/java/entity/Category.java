package entity;

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
@Table(name = "category")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_ID;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "mailAddress", nullable = false)
    private String mailAddress;
    @ElementCollection
    private List<String> keywords;
    @Column(name = "importance", nullable = false)
    private int importance;



}
