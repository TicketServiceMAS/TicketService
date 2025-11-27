package com.example.ticketservice.repository;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.util.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetricsDepartmentRepository extends JpaRepository<MetricsDepartment, Integer>{

}
