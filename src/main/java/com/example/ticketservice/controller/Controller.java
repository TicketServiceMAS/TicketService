package com.example.ticketservice.controller;

import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticketservice")
@CrossOrigin
public class Controller {

    private final MetricsService metricsService;
    private final DepartmentService departmentService;

    public Controller(MetricsService metricsService,
                      DepartmentService departmentService) {
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
    @PostMapping("/department/create")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.ok(createdDepartment);
    }
}
