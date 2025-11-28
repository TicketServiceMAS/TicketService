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

    @PostMapping("/department/create")
    public ResponseEntity<?> createDepartment(DepartmentDTO departmentDTO){
        Department createdDepartment = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @GetMapping("/departments")
        public List<Department> getDepartments(){
            return departmentService.getDepartments();
    }

}
