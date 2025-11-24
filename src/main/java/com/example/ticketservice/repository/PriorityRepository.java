package com.example.ticketservice.repository;
import com.example.ticketservice.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PriorityRepository extends JpaRepository<Priority, Integer>{
}
