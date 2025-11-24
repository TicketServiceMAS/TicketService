package com.example.ticketservice.repository;
import com.example.ticketservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Department, Integer>{
}
