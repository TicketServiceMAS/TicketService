package com.example.ticketservice.controller;

import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.service.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketservice")
@CrossOrigin
public class Controller {

    private final MetricsService metricsService;

    public Controller(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/stats")
    public RoutingStatsDTO getRoutingStats() {
        return metricsService.getRoutingStats();
    }

    @PostMapping("/department/create")
    public ResponseEntity<?> createDepartment(Department department){
        Department createdDepartment =
    }
}
