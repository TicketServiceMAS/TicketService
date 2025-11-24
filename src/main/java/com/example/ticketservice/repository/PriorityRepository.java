package com.example.ticketservice.repository;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.util.PriorityName;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PriorityRepository extends JpaRepository<Priority, Integer>{
    Priority getPriorityByPriorityName(PriorityName priorityName);
}
