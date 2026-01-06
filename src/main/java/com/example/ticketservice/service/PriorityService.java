package com.example.ticketservice.service;

import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityService (PriorityRepository priorityRepository){
        this.priorityRepository = priorityRepository;
    }


    public List<Priority> getPriorities(){
        return priorityRepository.findAll();
    }

    public Priority getPriority(int id){
        return priorityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Priority not found with ID " + id));
    }

    public Priority createPriority(Priority priority) {
        return priorityRepository.save(priority);
    }

    public Priority updatePriority(int id, Priority priority){
        Priority priorityToUpdate = priorityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Priority not found with ID " + id));
        priorityToUpdate.setPriorityName(priority.getPriorityName());
        return priorityRepository.save(priorityToUpdate);
    }

    public void deletePriority(int id){
        Priority priorityToDelete = priorityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Priority not found with ID " + id));
        priorityRepository.delete(priorityToDelete);
    }

}
