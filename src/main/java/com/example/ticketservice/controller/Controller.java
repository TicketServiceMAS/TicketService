package com.example.ticketservice.controller;

import com.example.ticketservice.dto.DepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
import com.example.ticketservice.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticketservice")
@CrossOrigin
public class Controller {

    private final MetricsService metricsService;
    private final DepartmentService departmentService;
    private final PriorityService priorityService;


    public Controller(MetricsService metricsService, DepartmentService departmentService, PriorityService priorityService) {
        this.metricsService = metricsService;
        this.departmentService = departmentService;
        this.priorityService = priorityService;
    }

    @GetMapping("/stats")
    public RoutingStatsDTO getRoutingStats() {
        return metricsService.getRoutingStats();
    }

    // NYT: hent alle departments
    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    // BONUS: opret department (hvis du vil bruge det senere)
    @PostMapping("/departments/create")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.ok(createdDepartment);
    }

    @PutMapping("/departments/{id}/update")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/departments/{id}/delete")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/priorities")
    public List<Priority> getPriorities() {
        return priorityService.getPriorities();
    }

    // BONUS: opret department (hvis du vil bruge det senere)
    @PostMapping("/priorities/create")
    public ResponseEntity<Priority> createPriority(@RequestBody Priority priority) {
        Priority createdPriority = priorityService.createPriority(priority);
        return ResponseEntity.ok(createdPriority);
    }

    @PutMapping("/priorities/{id}/update")
    public ResponseEntity<?> updatePriority(@PathVariable int id, @RequestBody Priority priority) {
        try {
            Priority updatedPriority = priorityService.updatePriority(id, priority);
            return ResponseEntity.ok(updatedPriority);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/priorities/{id}/delete")
    public ResponseEntity<?> deletePriority(@PathVariable int id) {
        try {
            priorityService.deletePriority(id);
            return ResponseEntity.ok("Priority deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}


