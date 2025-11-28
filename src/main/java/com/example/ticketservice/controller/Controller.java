package com.example.ticketservice.controller;

import com.example.ticketservice.dto.DepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
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


    public Controller(MetricsService metricsService, DepartmentService departmentService) {
        this.metricsService = metricsService;
        this.departmentService = departmentService;
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
}


