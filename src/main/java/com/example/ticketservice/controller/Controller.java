package com.example.ticketservice.controller;

import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.service.MetricsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
