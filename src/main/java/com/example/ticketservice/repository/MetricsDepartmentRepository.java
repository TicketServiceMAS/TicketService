package com.example.ticketservice.repository;

import com.example.ticketservice.entity.MetricsDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetricsDepartmentRepository extends JpaRepository<MetricsDepartment, Integer> {


    /**
     * Finder alle MetricsDepartment-rækker (tickets) for et givent department.
     *
     * Forudsætter at MetricsDepartment har en relation:
     *   private Department department;
     *
     * og at Department har feltet:
     *   private int categoryID;
     *
     * Så vil Spring Data automatisk binde:
     *   department.categoryID -> department_CategoryID i metodenavn.
     */

}
